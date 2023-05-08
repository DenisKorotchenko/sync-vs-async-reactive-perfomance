package org.dksu.teststand.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import mu.KLogging
import org.dksu.teststand.entity.DataEntity
import org.dksu.teststand.repository.ReactiveDataRepository
import org.springframework.stereotype.Service
import java.util.Random
import java.util.UUID

//const val MAX_STATE = 100L

@Service
class ReactiveDataService(
    private val dataRepository: ReactiveDataRepository
): KLogging() {
    private val random = Random()

    fun randomText(maxLength: Long = 50): String {
        val chars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + (' '..' ')
        val str = (0 until random.nextLong(0, maxLength)).map {
            chars.random()
        }.joinToString("")
        return str
    }

    suspend fun addRandomData(count: Int, maxState: Long? = null): Int {
        val data = (0 until count).map {
            DataEntity(
                uuid = UUID.randomUUID().toString(),
                txt = randomText(),
                state = random.nextLong(0, maxState ?: MAX_STATE)
            )
        }
        logger.info("Data created")
        return dataRepository.saveAll(data).count()
    }

    fun getWithRandomState(): Flow<DataEntity> {
        val state = random.nextLong(100)
        return dataRepository.findAllByState(state)
    }
}