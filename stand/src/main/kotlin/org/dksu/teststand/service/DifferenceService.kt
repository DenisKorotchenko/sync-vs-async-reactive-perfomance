package org.dksu.teststand.service

import io.micrometer.prometheus.PrometheusMeterRegistry
import org.dksu.teststand.entity.DataEntity
import org.dksu.teststand.repository.DataRepository
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class DifferenceService(
    private val dataRepository: DataRepository,
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

    fun sortByTxt(rows: List<DataEntity>): List<DataEntity> {
        return rows.sortedBy { it.txt }.toMutableList()
    }

    fun getDifferenceFromTo(fromState: Long, toState: Long, numberOfCompareRows: Long): Int {
        val d = Random.nextInt(50)
        val betweenRows = meterRegistry.timer("db").recordCallable {
            dataRepository.findAllByStateBetween(fromState + d, toState + d)
        }

        val ans = meterRegistry.timer("logic").recordCallable {
            sortByTxt(betweenRows ?: emptyList())
        }

        meterRegistry.timer("external").record {
            //externalService.call()
        }
        return ans?.size ?: 0
    }
}
