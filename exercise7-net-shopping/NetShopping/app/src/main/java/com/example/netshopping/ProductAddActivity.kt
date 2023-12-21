package com.example.netshopping

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.netshopping.category.Category
import com.example.netshopping.category.CategoryRepository
import com.example.netshopping.category.CategoryViewModel
import com.example.netshopping.category.CategoryViewModelFactory
import com.example.netshopping.product.ProductRepository
import com.example.netshopping.product.ProductViewModel
import com.example.netshopping.product.ProductViewModelFactory

class ProductAddActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)

        nameEditText = findViewById(R.id.name)
        descriptionEditText = findViewById(R.id.description)
        priceEditText = findViewById(R.id.price)
        categorySpinner = findViewById(R.id.category)
        submitButton = findViewById(R.id.submit_button)

        val repository = CategoryRepository.getInstance()
        val viewModelFactory = CategoryViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[CategoryViewModel::class.java]
        viewModel.categories.observe(this, Observer { categories ->
            val adapter = object :
                ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categories) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    (view as TextView).text = categories[position].name
                    return view
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    (view as TextView).text = categories[position].name
                    return view
                }
            }

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
        })

        val productsRepository = ProductRepository.getInstance()
        val productsViewModelFactory = ProductViewModelFactory(productsRepository)
        val productsViewModel =
            ViewModelProvider(this, productsViewModelFactory)[ProductViewModel::class.java]

        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val price = priceEditText.text.toString().toDoubleOrNull()
            val category = categorySpinner.selectedItem as Category

            if (name.isEmpty() || description.isEmpty() || price == null || category.id == null) {
                Toast
                    .makeText(this, "All fields must be filled", Toast.LENGTH_SHORT)
                    .show()
            } else {
                productsViewModel.addProduct(
                    name = name,
                    description = description,
                    price = price,
                    categoryId = category.id!!
                )

                finish()
            }
        }
    }
}
