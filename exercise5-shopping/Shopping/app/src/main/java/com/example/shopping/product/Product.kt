package com.example.shopping.product

import java.util.UUID

data class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Double,
    val author: String,
    val description: String,
    val imageResourceId: Int,
)