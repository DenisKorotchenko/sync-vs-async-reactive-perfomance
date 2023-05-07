package org.dksu.teststand.repository

import kotlinx.coroutines.flow.Flow
import org.dksu.teststand.entity.DataEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReactiveDataRepository: CoroutineCrudRepository<DataEntity, Long> {
    fun findAllByStateBetween(from: Long, to: Long): Flow<DataEntity>
    suspend fun findAllByStateBetweenOrderByUuid(from: Long, to: Long): List<DataEntity>
    suspend fun findALlByState(state: Long): List<DataEntity>
}