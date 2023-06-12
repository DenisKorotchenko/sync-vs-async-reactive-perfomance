package org.dksu.teststand.service

import io.micrometer.prometheus.PrometheusMeterRegistry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import org.dksu.teststand.entity.DataEntity
import org.dksu.teststand.repository.ReactiveDataRepository
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class ReactiveDifferenceService(
    private val dataRepository: ReactiveDataRepository,
    private val externalServiceEmulator: ExternalServiceEmulator,
    private val meterRegistry: PrometheusMeterRegistry,
) {
    fun countDifference(aRows: List<DataEntity>, bRows: List<DataEntity>): MutableList<DataEntity> {
        val aRowsSet = aRows.toSet()
        val ans = mutableListOf<DataEntity>()
        for (bRow in bRows) {
            if (bRow in aRowsSet) {
                ans.add(bRow)
            }
        }
        return ans
    }

    suspend fun sortByTxt(rows: Flow<DataEntity>): List<DataEntity> {
        val ans = mutableListOf<DataEntity>()
        rows.collect {
            ans.add(it)
        }
        return ans.sortedBy { it.txt }.toMutableList()
    }

    suspend fun sortByTxt(rows: List<DataEntity>): List<DataEntity> {
        return rows.sortedBy { it.txt }.toMutableList()
    }

    suspend fun getDifferenceFromTo(fromState: Long, toState: Long, numberOfCompareRows: Long): Int {
        val d = Random.nextInt(50)
        val betweenRows = dataRepository.findAllByStateBetween(fromState + d, toState + d)

        val ans = sortByTxt(betweenRows)

        return ans.size
    }
}
