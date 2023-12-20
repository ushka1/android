package com.example.netshopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.netshopping.adapters.CategoryAdapter
import com.example.netshopping.repositories.CategoryRepository
import com.example.netshopping.viewmodels.CategoryViewModel
import com.example.netshopping.viewmodels.CategoryViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = CategoryRepository.getInstance()
        val viewModelFactory = CategoryViewModelFactory(repository)
        categoryViewModel = ViewModelProvider(this, viewModelFactory)[CategoryViewModel::class.java]
        
        val adapter = CategoryAdapter(categoryViewModel.categories)
        val recyclerView = findViewById<RecyclerView>(R.id.categories_recycler)
        recyclerView.adapter = adapter
    }

}