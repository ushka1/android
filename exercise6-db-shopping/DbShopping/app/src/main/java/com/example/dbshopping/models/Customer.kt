package com.example.dbshopping.models

data class Customer(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var cart: Cart? = null
)