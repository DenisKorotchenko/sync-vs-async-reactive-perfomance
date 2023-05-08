package org.dksu.teststand

import mu.KLogging
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.PostgreSQLContainer
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration
import kotlin.concurrent.thread

class Runner {
    fun run() {
        val syncPostgreSQLContainer = PostgreSQLContainer("postgres:11").withNetwork(myNetwork)
            .withNetworkAliases("sync-db")
            .withExposedPorts(5432)
        syncPostgreSQLContainer.start()
        val reactivePostgreSQLContainer = PostgreSQLContainer("postgres:11").withNetwork(myNetwork)
            .withNetworkAliases("reactive-db")
            .withExposedPorts(5432)
        reactivePostgreSQLContainer.start()
        Thread.sleep(1000 * 10)
        val syncTestStandContainer = SyncTestStandContainer()
            .withEnv("SPRING_DATASOURCE_URL", "jdbc:postgresql://sync-db:5432/test")
            .withEnv("SPRING_DATASOURCE_DRIVER-CLASS-NAME", "org.postgresql.Driver")
        syncTestStandContainer.start()
        val reactiveTestStandContainer = ReactiveTestStandContainer()
            .withEnv("SPRING_R2DBC_URL", "r2dbc:postgresql://reactive-db:5432/test")
            .withEnv("SPRING_LIQUIBASE_URL", "jdbc:postgresql://reactive-db:5432/test")
            //.withEnv("SPRING_LIQUIBASE_ENABLED", "false")
            //.withEnv("SPRING_DATASOURCE_DRIVER-CLASS-NAME", "org.postgresql.Driver")
        reactiveTestStandContainer.start()
        val prometheusContainer = PrometheusContainer()
        prometheusContainer.start()
        val grafanaContainer = GrafanaContainer()
        grafanaContainer.startContainer()
        val uid = grafanaContainer.addPrometheusDataSource()

        println()
        println("Sync swagger: ${syncTestStandContainer.getSwaggerUrl()}")
        println("Reactive swagger: ${reactiveTestStandContainer.getSwaggerUrl()}")
        println()


        val dashUrl = grafanaContainer.addDashboard(uid)

        println()
        println("Grafana: ${grafanaContainer.getUrl()}/$dashUrl")
        println()

        val gatlingContainer = GatlingContainer()
        gatlingContainer.withFileSystemBind("build/reports/", "/home/gradle/project/build/reports/", BindMode.READ_WRITE)
        gatlingContainer.start()
        println("Gatling started")
        Thread.sleep(1000 * 6000)
        
        //gatlingContainer.copyFileFromContainer("/home/gradle/project/build/reports/", "reports/")
    }
}

fun main() {

    val request = HttpRequest.newBuilder(URI("http://localhost:8080/test-stand/data/delay"))
        .GET()
        .build()

    val logger = KLogging().logger

    //Thread.sleep(5000);
    repeat(20) {
        thread {
            try {
                val httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(100)).build();

                val response = httpClient.send(request, BodyHandlers.ofString())
                println(response.statusCode())
            } catch (e: Exception) {
                logger.error("err", e)
                return@thread
            }
        }
    }

    Thread.sleep(10000);
    repeat(20) {
        thread {
            try {
                val httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(100)).build();

                val response = httpClient.send(request, BodyHandlers.ofString())
                println(response.statusCode())
            } catch (e: Exception) {
                logger.error("err", e)
                return@thread
            }
        }
    }
}