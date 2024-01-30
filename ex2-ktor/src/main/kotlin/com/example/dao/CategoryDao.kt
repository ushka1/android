package com.example.dao

import com.example.models.Category

interface CategoryDao {
    suspend fun allCategories(): List<Category>
    suspend fun category(id: Int): Category?
    suspend fun addNewCategory(name: String, code: String, description: String?): Category?
    suspend fun editCategory(id: Int, name: String?, code: String?, description: String?): Boolean
    suspend fun deleteCategory(id: Int): Boolean
}
