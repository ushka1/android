package com.example.netshopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.netshopping.category.CategoryAdapter
import com.example.netshopping.category.CategoryItemListener
import com.example.netshopping.category.CategoryRepository
import com.example.netshopping.category.CategoryViewModel
import com.example.netshopping.category.CategoryViewModelFactory
import com.example.netshopping.product.ProductAdapter
import com.example.netshopping.product.ProductRepository
import com.example.netshopping.product.ProductViewModel
import com.example.netshopping.product.ProductViewModelFactory

class MainActivity : AppCompatActivity(), CategoryItemListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupCategories()
        setupProducts()
    }

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter

    private fun setupCategories() {
        val repository = CategoryRepository.getInstance()
        val viewModelFactory = CategoryViewModelFactory(repository)
        categoryViewModel =
            ViewModelProvider(this, viewModelFactory)[CategoryViewModel::class.java]

        categoryAdapter = CategoryAdapter(
            categoryViewModel.categories,
            categoryViewModel.selectedCategoryId,
            this
        )
        val recyclerView = findViewById<RecyclerView>(R.id.categories_recycler)
        recyclerView.adapter = categoryAdapter
    }

    override fun onCategorySelect(categoryId: String) {
        categoryViewModel.selectCategory(categoryId)
    }

    private lateinit var productViewModel: ProductViewModel

    private fun setupProducts() {
        val repository = ProductRepository.getInstance()
        val viewModelFactory = ProductViewModelFactory(repository)
        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]

        val adapter = ProductAdapter(productViewModel.products)
        val recyclerView = findViewById<RecyclerView>(R.id.products_recycler)
        recyclerView.adapter = adapter

        categoryViewModel.selectedCategoryId.observe(this, Observer { categoryId ->
            productViewModel.filterProductsByCategory(categoryId)
        })
    }

}