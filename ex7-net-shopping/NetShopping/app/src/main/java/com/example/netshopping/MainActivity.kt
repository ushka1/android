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

        setupCategory()
        setupProduct()
        setupFAB()
    }

    override fun onResume() {
        super.onResume()
        productViewModel.refetchProducts()
    }

    override fun onCategorySelect(categoryId: String) {
        categoryViewModel.selectCategory(categoryId)
    }

    private fun setupCategory() {
        val categoryRepository = CategoryRepository.getInstance()
        val categoryViewModelFactory = CategoryViewModelFactory(categoryRepository)
        categoryViewModel =
            ViewModelProvider(this, categoryViewModelFactory)[CategoryViewModel::class.java]

        val categoryAdapter = CategoryAdapter(
            categoryViewModel.categories,
            categoryViewModel.selectedCategoryId,
            this
        )
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.categories_recycler)
        categoryRecyclerView.adapter = categoryAdapter
    }


    private fun setupProduct() {
        val productRepository = ProductRepository.getInstance()
        val productViewModelFactory = ProductViewModelFactory(productRepository)
        productViewModel =
            ViewModelProvider(this, productViewModelFactory)[ProductViewModel::class.java]

        val productAdapter = ProductAdapter(productViewModel.products)
        val productRecyclerView = findViewById<RecyclerView>(R.id.products_recycler)
        productRecyclerView.adapter = productAdapter

        categoryViewModel.selectedCategoryId.observe(this, Observer { categoryId ->
            productViewModel.filterProductsByCategory(categoryId)
        })
    }

    private fun setupFAB() {
        val button: FloatingActionButton = findViewById(R.id.add_product_button)
        button.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }
    }

}