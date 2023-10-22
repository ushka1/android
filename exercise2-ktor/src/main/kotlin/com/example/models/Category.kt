package com.example.models


import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Category(
    val id: Int? = null,
    val name: String,
    val code: String,
    val description: String? = null,
)

object Categories : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val code = varchar("code", 128).uniqueIndex()
    val description = varchar("description", 1024).nullable()

    override val primaryKey = PrimaryKey(id)
}

