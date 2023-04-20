package org.dksu.teststand.service

import org.dksu.teststand.entity.DataEntity
import org.dksu.teststand.repository.DataRepository
import org.springframework.stereotype.Service

@Service
class DifferenceService(
    private val dataRepository: DataRepository,
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

    fun getDifferenceFromTo(fromState: Long, toState: Long, numberOfCompareRows: Long): Int {
        val betweenRows = dataRepository.findAllByStateBetween(fromState, toState)
        val rows = mutableSetOf<Long>()
        val randomRows = mutableListOf<DataEntity>()
        for (i in 0 until numberOfCompareRows) {
            val row = (0 until MAX_STATE).random()
            if (row !in rows) {
                rows.add(row)
                randomRows += dataRepository.findALlByState(row)
            }
        }
        // TODO(Compare here, please)
        return countDifference(betweenRows, randomRows).size
    }
}
