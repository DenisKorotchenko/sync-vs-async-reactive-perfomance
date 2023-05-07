package org.dksu.teststand

import org.testcontainers.containers.BindMode
import org.testcontainers.containers.GenericContainer

class PrometheusContainer: GenericContainer<PrometheusContainer>("prom/prometheus") {
    init {
        withPrivilegedMode(true)
        withNetwork(myNetwork)
        withNetworkAliases("prometheus")
        withExposedPorts(9090)
        withClasspathResourceMapping("prometheus.yml", "/etc/prometheus/prometheus.yml", BindMode.READ_ONLY)
    }
}