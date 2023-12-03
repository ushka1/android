package com.example.shopping.cart;

import com.example.shopping.product.Product

class Cart() {

    companion object {
        @Volatile
        private var instance: Cart? = null
        fun getInstance(): Cart {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = Cart()
                    }
                }
            }
            return instance!!
        }
    }

    inner class CartItem(
        val product: Product,
        var quanitity: Int
    )

    private val items: MutableList<CartItem> = mutableListOf()

    fun getItems(): List<CartItem> {
        return items
    }

    fun addItem(product: Product, quantity: Int = 1) {
        items.add(CartItem(product, quantity))
    }

    fun removeItem(product: Product) {
        items.removeIf { it.product == product }
    }

    fun updateItemQuantity(product: Product, quantity: Int) {
        val item = items.find { it.product == product }
        if (item != null) {
            if (quantity > 0) {
                item.quanitity = quantity
            } else {
                removeItem(item.product)
            }
        }
    }

}