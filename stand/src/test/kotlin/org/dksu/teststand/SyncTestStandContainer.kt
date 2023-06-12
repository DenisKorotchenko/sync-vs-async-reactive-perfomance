package org.dksu.teststand

import org.testcontainers.containers.BindMode
import org.testcontainers.containers.GenericContainer

class SyncTestStandContainer() : GenericContainer<SyncTestStandContainer>("sync-teststand") {
    init {
        withNetwork(testStandNetwork)
        withNetworkAliases("sync")
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
        "http://" + host + ":" + getMappedPort(8080) + "/test-stand/swagger-ui/index.html"
}