package org.dksu.teststand

import org.testcontainers.containers.BindMode
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.SelinuxContext
import org.testcontainers.containers.output.Slf4jLogConsumer

class TestStandContainer() : GenericContainer<TestStandContainer>("teststand") {
    init {
        withNetwork(myNetwork)
        withNetworkAliases("teststand")
        withExposedPorts(8080)
        //withEnv("TESTCONTAINERS_RYUK_DISABLED", "true")
        withFileSystemBind(
            "//var/run/docker.sock",
            "/var/run/docker.sock",
            BindMode.READ_WRITE,
        )
        withLogConsumer(Slf4jLogConsumer(logger()))
    }

    override fun start() {
        super.start()
        println(getSwaggerUrl())
    }

    private fun getSwaggerUrl() =
        "http://" + host + ":" + getMappedPort(8080) + "/test-stand/swagger-ui/index.html"
}