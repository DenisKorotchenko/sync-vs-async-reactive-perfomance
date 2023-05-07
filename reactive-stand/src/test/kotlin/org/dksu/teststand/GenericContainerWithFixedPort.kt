package org.dksu.teststand

import org.testcontainers.containers.GenericContainer

open class GenericContainerWithFixedPort<T: GenericContainer<T>?>(
    image: String
): GenericContainer<T>(image) {
    override fun start() {
        exposedPorts.forEach {
            addFixedExposedPort(it, it)
        }
        withCreateContainerCmdModifier {
            it.withHostConfig((it.hostConfig!!.withAutoRemove(true)))
        }
        super.start()
    }
}