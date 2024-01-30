package com.example.netshopping.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoriesRepository: CategoryRepository) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _selectedCategoryId = MutableLiveData<String?>()
    val selectedCategoryId: LiveData<String?> = _selectedCategoryId

    init {
        viewModelScope.launch {
            _categories.value = categoriesRepository.getCategories()
        }
    }

    fun selectCategory(categoryId: String?) {
        _selectedCategoryId.value = if (_selectedCategoryId.value == categoryId) {
            null
        } else {
            categoryId
        }
    }

}