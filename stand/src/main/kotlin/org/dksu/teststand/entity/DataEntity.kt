package org.dksu.teststand.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "data")
open class DataEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open var id: Long? = null,

    @Column(name = "uuid")
    open var uuid: String,

    @Column(name = "txt")
    open var txt: String,

    @Column(name = "state")
    open var state: Long,
)

