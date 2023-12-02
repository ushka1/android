package com.example.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shopping.productdetails.ProductDetailsFragment

class ProductDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val productId = intent.getStringExtra("productId")
        if (productId != null) {
            val fragment = ProductDetailsFragment.newInstance(productId)
            
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

}