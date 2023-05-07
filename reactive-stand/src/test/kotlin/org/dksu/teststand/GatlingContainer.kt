package org.dksu.teststand

import org.testcontainers.containers.GenericContainer

class GatlingContainer() : GenericContainer<GatlingContainer>("teststand-gatling") {
    init {
        withPrivilegedMode(true)
        withNetwork(myNetwork)
        withNetworkAliases("gatling")
    }

    override fun start() {
        super.start()
    }

//    private fun getSwaggerUrl() =
//        "http://" + host + ":" + getMappedPort(8080) + "/test-stand/swagger-ui/index.html"
}