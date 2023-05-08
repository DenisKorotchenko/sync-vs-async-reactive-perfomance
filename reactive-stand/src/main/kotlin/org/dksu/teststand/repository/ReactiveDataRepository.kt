package org.dksu.teststand.repository

import kotlinx.coroutines.flow.Flow
import org.dksu.teststand.entity.DataEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReactiveDataRepository: CoroutineCrudRepository<DataEntity, Long> {
    suspend fun findAllByStateBetween(from: Long, to: Long): List<DataEntity>
    suspend fun findAllByStateBetweenOrderByUuid(from: Long, to: Long): List<DataEntity>
    fun findAllByState(state: Long): Flow<DataEntity>
}