package com.example.shopping.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {

    private val productRepository = ProductRepository.getInstance()
    private val _productsLiveData = MutableLiveData<List<Product>>()
    val productsLiveData: LiveData<List<Product>> get() = _productsLiveData

    init {
        loadAllProducts()
    }

    private fun loadAllProducts() {
        val allProducts = productRepository.getAllProducts()
        _productsLiveData.value = allProducts
    }

}