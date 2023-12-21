package com.example.netshopping

import android.content.Intent
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
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), CategoryItemListener {

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupCategories()
        setupProducts()

        val button: FloatingActionButton = findViewById(R.id.add_product_button)
        button.setOnClickListener {
            val intent = Intent(this, ProductAddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        productViewModel.refetchProducts()
    }


    override fun onCategorySelect(categoryId: String) {
        categoryViewModel.selectCategory(categoryId)
    }

    private fun setupCategories() {
        val repository = CategoryRepository.getInstance()
        val viewModelFactory = CategoryViewModelFactory(repository)
        categoryViewModel = ViewModelProvider(this, viewModelFactory)[CategoryViewModel::class.java]

        val adapter = CategoryAdapter(
            categoryViewModel.categories,
            categoryViewModel.selectedCategoryId,
            this
        )
        val recyclerView = findViewById<RecyclerView>(R.id.categories_recycler)
        recyclerView.adapter = adapter
    }


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