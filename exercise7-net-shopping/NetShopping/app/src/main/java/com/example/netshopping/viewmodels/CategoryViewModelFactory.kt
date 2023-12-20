package com.example.netshopping.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.netshopping.repositories.CategoryRepository

class CategoryViewModelFactory(private val categoryRepository: CategoryRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(categoryRepository) as T
        }
        
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
