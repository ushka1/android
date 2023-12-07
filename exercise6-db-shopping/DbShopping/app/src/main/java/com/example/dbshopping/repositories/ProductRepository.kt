package com.example.dbshopping.repositories

import com.example.dbshopping.models.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductRepository private constructor() {
    companion object {
        @Volatile
        private var INSTANCE: ProductRepository? = null

        fun getInstance(): ProductRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = ProductRepository()
                INSTANCE = instance
                instance
            }
        }
    }


    private val db = FirebaseFirestore.getInstance()
    private val productCollection = db.collection("products")

    suspend fun addProduct(product: Product): String {
        val document = productCollection.add(product).await()
        return document.id
    }

    suspend fun getProduct(id: String): Product? {
        val document = productCollection.document(id).get().await()

        return if (document.exists()) {
            document.toObject(Product::class.java)
        } else null
    }

    suspend fun getAllProducts(): List<Product> {
        val query = productCollection.get().await()
        return query.toObjects(Product::class.java)
    }

    suspend fun updateProduct(product: Product) {
        product.id?.let {
            productCollection.document(it).set(product).await()
        }
    }

    suspend fun deleteProduct(id: String) {
        productCollection.document(id).delete().await()
    }
}