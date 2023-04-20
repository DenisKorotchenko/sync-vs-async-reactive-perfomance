package org.dksu.teststand.service

import mu.KLogging
import org.dksu.teststand.entity.DataEntity
import org.dksu.teststand.repository.DataRepository
import org.springframework.stereotype.Service
import java.util.Random
import java.util.UUID

const val MAX_STATE = 100L

@Service
class DataService(
    private val dataRepository: DataRepository
): KLogging() {
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
}