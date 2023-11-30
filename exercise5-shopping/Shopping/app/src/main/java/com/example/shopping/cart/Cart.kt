package com.example.shopping.cart;

import com.example.shopping.product.Product

class Cart() {

    inner class CartItem(
        val product: Product,
        var quanitity: Int
    )

    private val items: MutableList<CartItem> = mutableListOf()

    fun getCartItems(): List<CartItem> {
        return items
    }

    fun addProduct(product: Product, quantity: Int = 1) {
        items.add(CartItem(product, quantity))
    }

    fun removeProduct(product: Product) {
        items.removeIf { it.product == product }
    }

    fun updateProductQuantity(product: Product, quantity: Int) {
        val item = items.find { it.product == product }
        if (item != null) {
            item.quanitity = quantity
        }
    }

}