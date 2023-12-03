package com.example.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shopping.cart.CartViewModel
import com.example.shopping.product.ProductDetailsFragment

class ProductDetailsActivity : AppCompatActivity() {

    private val cartViewModel = CartViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        title = "Product Details"

        val productId = intent.getStringExtra("productId")
        if (productId != null) {
            val fragment = ProductDetailsFragment.newInstance(productId, cartViewModel)

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

}