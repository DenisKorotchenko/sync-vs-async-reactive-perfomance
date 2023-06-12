package org.dksu.teststand.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("data")
open class DataEntity(
    @Id
    @Column("id")
    open var id: Long? = null,

    @Column("uuid")
    open var uuid: String,

    @Column("txt")
    open var txt: String,

    @Column("state")
    open var state: Long,
)

