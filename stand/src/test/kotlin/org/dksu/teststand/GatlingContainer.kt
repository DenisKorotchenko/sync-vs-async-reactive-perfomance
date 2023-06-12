package org.dksu.teststand

import org.testcontainers.containers.GenericContainer

class GatlingContainer() : GenericContainer<GatlingContainer>("teststand-gatling") {
    init {
        withPrivilegedMode(true)
        withNetwork(testStandNetwork)
        withNetworkAliases("gatling")
    }
}