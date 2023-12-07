package com.example.dbshopping.models

data class Cart(
    var id: String? = null,
    var products: List<Product>? = null
)