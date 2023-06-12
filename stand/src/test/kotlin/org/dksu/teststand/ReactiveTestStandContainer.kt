package org.dksu.teststand

import org.testcontainers.containers.BindMode
import org.testcontainers.containers.GenericContainer

class ReactiveTestStandContainer() : GenericContainer<ReactiveTestStandContainer>("reactive-teststand") {
    init {
        withNetwork(testStandNetwork)
        withNetworkAliases("reactive")
        withExposedPorts(8080, 8081)
        withFileSystemBind(
            "//var/run/docker.sock",
            "/var/run/docker.sock",
            BindMode.READ_WRITE,
        )
        withCreateContainerCmdModifier { cmd ->
            cmd.hostConfig?.withMemory(1024 * 1024 * 1024)
            cmd.hostConfig?.withNanoCPUs(1000_000_000)
        }
    }

    override fun start() {
        super.start()
        println(getSwaggerUrl())
    }

    fun getSwaggerUrl() =
        "http://" + host + ":" + getMappedPort(8080) + "/webjars/swagger-ui/index.html"
}