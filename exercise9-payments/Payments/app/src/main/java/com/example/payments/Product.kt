package com.example.payments

import kotlinx.serialization.Serializable

@Serializable
data class Product(val name: String, val price: Int, val quantity: Int)