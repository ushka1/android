package com.example.payments

import kotlinx.serialization.Serializable

@Serializable
data class ProductList(val products: List<Product>)