package org.dksu.teststand.repository

import org.dksu.teststand.entity.DataEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DataRepository: JpaRepository<DataEntity, Long> {
    fun findAllByStateBetween(from: Long, to: Long): List<DataEntity>
    fun findALlByState(state: Long): List<DataEntity>
}