package com.example.dbshopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dbshopping.repositories.CustomerRepository
import com.example.dbshopping.repositories.ProductRepository

class MainActivity : AppCompatActivity() {

    val productRepository = ProductRepository.getInstance()
    val customerRepository = CustomerRepository.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}