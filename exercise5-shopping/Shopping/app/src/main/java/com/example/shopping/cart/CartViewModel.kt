package com.example.shopping.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopping.product.ProductRepository

class CartViewModel : ViewModel() {

    private val cart = Cart.getInstance()
    private val productRepository = ProductRepository.getInstance()

    val cartItems = MutableLiveData(cart.getItems())

    fun addProductToCart(productId: String, quantity: Int = 1) {
        val product = productRepository.getProductById(productId)

        if (product != null && quantity > 0) {
            cart.addItem(product, quantity)
            cartItems.value = cart.getItems()
        }
    }

    fun updateProductQuantity(productId: String, quantity: Int) {
        val product = productRepository.getProductById(productId)

        if (product != null) {
            cart.updateItemQuantity(product, quantity)
            cartItems.value = cart.getItems()
        }
    }

    fun removeProductFromCart(productId: String) {
        val product = productRepository.getProductById(productId)

        if (product != null) {
            cart.updateItemQuantity(product, 0)
            cartItems.value = cart.getItems()
        }
    }

}