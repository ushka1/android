package com.example.netshopping.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private lateinit var originalProducts: List<Product>

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        refetchProducts()
    }

    fun refetchProducts() {
        viewModelScope.launch {
            val products = productRepository.getProducts()
            originalProducts = products
            _products.value = products
        }
    }

    fun addProduct(name: String, description: String, price: Double, categoryId: String) {
        viewModelScope.launch {
            val product =
                Product(
                    name = name,
                    description = description,
                    price = price,
                    categoryId = categoryId
                )
            productRepository.addProduct(product)
        }
    }

    fun filterProductsByCategory(categoryId: String?) {
        if (categoryId != null) {
            val filteredList = originalProducts.filter { it.categoryId == categoryId }
            _products.value = filteredList
        } else {
            _products.value = originalProducts
        }
    }

}