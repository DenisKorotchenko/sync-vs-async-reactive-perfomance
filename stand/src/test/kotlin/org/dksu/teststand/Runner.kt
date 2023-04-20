package org.dksu.teststand

import com.github.dockerjava.api.DockerClient
import org.testcontainers.DockerClientFactory
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.SelinuxContext
import org.testcontainers.shaded.com.github.dockerjava.core.DockerClientImpl

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
        val gatlingContainer = GatlingContainer()
        gatlingContainer.withFileSystemBind("build/reports/", "/home/gradle/project/build/reports/", BindMode.READ_WRITE)
        gatlingContainer.start()
        println("Gatling started")
//        gatlingContainer.copyFileFromContainer("/home/gradle/project/build/reports/", "reports/")

        println("Copied")
        Thread.sleep(1000 * 300)
        
        //gatlingContainer.copyFileFromContainer("/home/gradle/project/build/reports/", "reports/")
    }

    fun runGatling() {

    }
}