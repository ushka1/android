package com.example.payments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ProductViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()

    val products: LiveData<List<Product>>
        get() = _products

    init {
        _products.value = listOf(
            Product("Catan", 143 * 100, 0),
            Product("Dixit", 196 * 100, 0),
            Product("Pandemic", 204 * 100, 0),
            Product("Talisman", 455 * 100, 0),
            Product("Monopoly", 123 * 100, 0)
        )
    }

    fun increaseQuantity(product: Product) {
        product.quantity++
        _products.value = _products.value
    }

    fun decreaseQuantity(product: Product) {
        if (product.quantity > 0) {
            product.quantity--
            _products.value = _products.value
        }
    }

    fun isQuantityNotEmpty(): Boolean {
        var totalQuantity = 0;
        for (p in products.value!!) {
            totalQuantity += p.quantity
        }
        return totalQuantity > 0
    }

    fun getTotalPrice(): Int {
        var totalPrice = 0;
        for (p in products.value!!) {
            totalPrice += p.price * p.quantity
        }
        return totalPrice
    }
}