package com.example.shopping.cart

interface CartItemListener {

    fun onProductQuantityChanged(productId: String, quantity: Int)

}