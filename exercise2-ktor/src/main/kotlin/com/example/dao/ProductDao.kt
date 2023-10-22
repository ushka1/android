package com.example.dao

import com.example.models.Product

interface ProductDao {
    suspend fun allProducts(): List<Product>
    suspend fun product(id: Int): Product?
    suspend fun addNewProduct(name: String, description: String, price: Float, categoryCode: String): Product?
    suspend fun editProduct(id: Int, name: String, description: String, price: Float, categoryCode: String): Boolean
    suspend fun deleteProduct(id: Int): Boolean
}