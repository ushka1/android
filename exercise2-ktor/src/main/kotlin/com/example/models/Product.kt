package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Float,
)

val productStorage = mutableListOf<Product>()