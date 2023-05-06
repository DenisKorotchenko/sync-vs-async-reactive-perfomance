package org.dksu.teststand

import com.github.dockerjava.api.DockerClient
import mu.KLogger
import mu.KLogging
import mu.KotlinLogging
import org.testcontainers.DockerClientFactory
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.SelinuxContext
import org.testcontainers.containers.output.WaitingConsumer
import org.testcontainers.shaded.com.github.dockerjava.core.DockerClientImpl
import java.lang.RuntimeException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration
import kotlin.concurrent.thread
import kotlin.math.log

class Runner {
    fun run() {
        val postgreSQLContainer = PostgreSQLContainer("postgres:11").withNetwork(myNetwork)
            .withNetworkAliases("db")
            .withExposedPorts(5432)
        postgreSQLContainer.start()
        Thread.sleep(1000 * 10)
        val testStandContainer = TestStandContainer()
            .withEnv("SPRING_DATASOURCE_URL", "jdbc:postgresql://db:5432/test")
            .withEnv("SPRING_DATASOURCE_DRIVER-CLASS-NAME", "org.postgresql.Driver")
        testStandContainer.start()
        val prometheusContainer = PrometheusContainer()
        prometheusContainer.start()
        val grafanaContainer = GrafanaContainer()
        grafanaContainer.startContainer()
        val uid = grafanaContainer.addPrometheusDataSource()

        println()
        println("Grafana pred: ${grafanaContainer.getUrl()}")
        println()


        val dashUrl = grafanaContainer.addDashboard(uid)

        println()
        println("Grafana: ${grafanaContainer.getUrl()}/$dashUrl")
        println()

        val gatlingContainer = GatlingContainer()
        gatlingContainer.withFileSystemBind("build/reports/", "/home/gradle/project/build/reports/", BindMode.READ_WRITE)
        gatlingContainer.start()
        println("Gatling started")
        gatlingContainer.copyFileFromContainer("/home/gradle/project/build/reports/", "reports/")
        Thread.sleep(1000 * 3000)
        
        //gatlingContainer.copyFileFromContainer("/home/gradle/project/build/reports/", "reports/")
    }

    fun runGatling() {

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