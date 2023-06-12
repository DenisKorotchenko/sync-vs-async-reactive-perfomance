package org.dksu.teststand.service

import io.micrometer.prometheus.PrometheusMeterRegistry
import mu.KLogging
import org.dksu.teststand.entity.DataEntity
import org.dksu.teststand.repository.DataRepository
import org.springframework.stereotype.Service
import java.util.*

const val MAX_STATE = 100L

@Service
class DataService(
    private val dataRepository: DataRepository,
    private val meterRegistry: PrometheusMeterRegistry,
) : KLogging() {
    private val random = Random()

    fun randomText(maxLength: Long = 50): String {
        val chars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + (' '..' ')
        val str = (0 until random.nextLong(0, maxLength)).map {
            chars.random()
        }.joinToString("")
        return str
    }

    fun addRandomData(count: Int, maxState: Long? = null): Int {
        val data = (0 until count).map {
            DataEntity(
                uuid = UUID.randomUUID().toString(),
                txt = randomText(),
                state = random.nextLong(0, maxState ?: MAX_STATE)
            )
        }
        logger.info("Data created")
        return dataRepository.saveAll(data).size
    }

    fun getWithRandomState(): Iterable<DataEntity> {
        val state = random.nextLong(100)
        return dataRepository.findAllByState(state)
    }

    fun getWithProcessing(): Iterable<Int> {
        val data = meterRegistry.timer("db").recordCallable {
            getWithRandomState()
        }!!
        return meterRegistry.timer("logic").recordCallable {
            data.map {
                var ans = 0
                for (s0 in it.txt) {
                    for (s1 in it.txt) {
                        for (s2 in it.txt) {
                            if (s0 == s1 && s1 == s2)
                                ans++
                        }
                    }
                }
                ans
            }
        }!!
    }
}