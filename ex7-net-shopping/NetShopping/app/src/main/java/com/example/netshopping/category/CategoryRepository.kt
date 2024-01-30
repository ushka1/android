package com.example.netshopping.category

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CategoryRepository private constructor() {

    companion object {
        @Volatile
        private var INSTANCE: CategoryRepository? = null

        fun getInstance(): CategoryRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = CategoryRepository()
                INSTANCE = instance
                instance
            }
        }
    }

    private val db = FirebaseFirestore.getInstance()
    private val categoryCollection = db.collection("categories")

    suspend fun getCategories(): List<Category> {
        val result = mutableListOf<Category>()
        val querySnapshot = categoryCollection.get().await()
        for (documentSnapshot in querySnapshot.documents) {
            val category = documentSnapshot.toObject(Category::class.java)
            category?.id = documentSnapshot.id
            category?.let { result.add(it) }
        }
        return result
    }

}