package com.example.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.product.ProductAdapter
import com.example.shopping.product.ProductRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productRecyclerView = findViewById<RecyclerView>(R.id.product_recycler_view)
        productRecyclerView.layoutManager = LinearLayoutManager(this)

        val productList = ProductRepository.getInstance().getAllProducts()
        val adapter = ProductAdapter(productList)
        productRecyclerView.adapter = adapter
    }
}