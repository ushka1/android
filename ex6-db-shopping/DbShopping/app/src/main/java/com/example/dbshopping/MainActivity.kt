package com.example.dbshopping

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.dbshopping.models.Cart
import com.example.dbshopping.models.Customer
import com.example.dbshopping.models.Product
import com.example.dbshopping.repositories.CustomerRepository
import com.example.dbshopping.repositories.ProductRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private val productRepository = ProductRepository.getInstance()
    private val customerRepository = CustomerRepository.getInstance()

    private var exampleCustomerId: String? = null
    private val exampleCustomer = Customer().apply {
        name = "Gustave H."
        email = "gustaveh@mail.com"
        cart = Cart().apply {
            products = listOf<Product>()
        }
    }

    private var exampleProductId: String? = null
    private val exampleProduct = Product().apply {
        name = "Boy with Apple"
        description =
            "The Boy with an Apple is a 21st century painting by the British artist Michael Taylor."
        price = 23_999_999.0
        categoryId = "1"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addCustomerButton = findViewById<Button>(R.id.add_customer_button)
        addCustomerButton.setOnClickListener {
            runBlocking {
                launch {
                    val id = customerRepository.addCustomer(exampleCustomer)
                    exampleCustomerId = id
                }
            }
        }

        val removeCustomerButton = findViewById<Button>(R.id.remove_customer_button)
        removeCustomerButton.setOnClickListener {
            runBlocking {
                launch {
                    if (exampleCustomerId != null) {
                        customerRepository.deleteCustomer(exampleCustomerId!!)
                    }
                }
            }
        }

        val addProductButton = findViewById<Button>(R.id.add_product_button)
        addProductButton.setOnClickListener {
            runBlocking {
                launch {
                    val id = productRepository.addProduct(exampleProduct)
                    exampleProductId = id
                }
            }
        }

        val removeProductButton = findViewById<Button>(R.id.remove_product_button)
        removeProductButton.setOnClickListener {
            runBlocking {
                launch {
                    if (exampleProductId != null) {
                        productRepository.deleteProduct(exampleProductId!!)
                    }
                }
            }
        }
    }
}