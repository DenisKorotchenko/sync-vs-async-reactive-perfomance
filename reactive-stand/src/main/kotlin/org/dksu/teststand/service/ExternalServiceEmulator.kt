package org.dksu.teststand.service

import org.springframework.stereotype.Service
import space.kscience.kmath.random.RandomGenerator
import space.kscience.kmath.samplers.GaussianSampler

@Service
class ExternalServiceEmulator {
    val generator = RandomGenerator.default
    val distribution = GaussianSampler(100.0, 5.0)

    // emulate work and network problems
    fun call() {
        val timeMs = distribution.sample(generator).nextBlocking().toLong()
        println(timeMs)
        Thread.sleep(timeMs)
    }
}