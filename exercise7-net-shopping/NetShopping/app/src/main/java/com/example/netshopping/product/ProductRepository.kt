package com.example.netshopping.product

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
        val documentReference = productCollection.add(product).await()
        return documentReference.id
    }

    suspend fun getProduct(id: String): Product? {
        val documentSnapshot = productCollection.document(id).get().await()
        if (documentSnapshot.exists()) {
            val product = documentSnapshot.toObject(Product::class.java)
            product?.id = documentSnapshot.id
            product?.let { return product }
        }

        return null
    }

    suspend fun getProducts(): List<Product> {
        val result = mutableListOf<Product>()
        val querySnapshot = productCollection.get().await()
        for (documentSnapshot in querySnapshot.documents) {
            val product = documentSnapshot.toObject(Product::class.java)
            product?.id = documentSnapshot.id
            product?.let { result.add(it) }
        }

        return result
    }

}