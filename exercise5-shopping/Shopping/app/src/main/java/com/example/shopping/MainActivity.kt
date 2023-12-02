package com.example.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.product.ProductAdapter
import com.example.shopping.product.ProductRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productRecyclerView = findViewById<RecyclerView>(R.id.product_recycler_view)
        val productLayoutManger = LinearLayoutManager(this)
        productRecyclerView.layoutManager = productLayoutManger

        val productList = ProductRepository.getInstance().getAllProducts()
        val adapter = ProductAdapter(productList)
        productRecyclerView.adapter = adapter

        val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.line_divider)
        val dividerItemDecoration = DividerItemDecoration(
            productRecyclerView.context,
            productLayoutManger.orientation
        )
        if (dividerDrawable != null) {
            dividerItemDecoration.setDrawable(dividerDrawable)
        }
        productRecyclerView.addItemDecoration(dividerItemDecoration)
    }
}