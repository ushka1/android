package com.example.models


import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Category(
    val id: Int? = null,
    val name: String,
)

object Categories : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)

    override val primaryKey = PrimaryKey(id)
}

