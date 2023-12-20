package com.example.netshopping.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netshopping.models.Category
import com.example.netshopping.repositories.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoriesRepository: CategoryRepository) : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    init {
        viewModelScope.launch {
            _categories.value = categoriesRepository.getAllCategories()
        }
    }
}