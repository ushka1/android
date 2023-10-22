package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Product(
    val id: Int? = null,
    val name: String,
    val description: String,
    val price: Float,
)

object Products : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val description = varchar("description", 1024)
    val price = float("price")

    override val primaryKey = PrimaryKey(id)
}

