package com.example.payments

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {
    private lateinit var paymentSheet: PaymentSheet
    private lateinit var paymentButton: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        productAdapter = ProductAdapter(productViewModel)
        productViewModel.products.observe(this) { products ->
            productAdapter.updateProducts(products)

            if (productViewModel.isQuantityNotEmpty()) {
                val totalPrice = productViewModel.getTotalPrice()
                paymentButton.isEnabled = true
                paymentButton.text = "Pay ${totalPrice / 100}.${totalPrice % 100} PLN"
            } else {
                paymentButton.isEnabled = false
                paymentButton.text = "Pay"
            }
        }
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = productAdapter

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        paymentButton = findViewById(R.id.payment_button)
        paymentButton.setOnClickListener {
            paymentButton.isEnabled = false
            startPayment()
        }
        paymentButton.isEnabled = false
    }

    private fun startPayment() {
        val productList = ProductList(productViewModel.products.value!!)
        val jsonBody = Json.encodeToString(ProductList.serializer(), productList)

        "http://10.0.2.2:3000/payment-sheet"
            .httpPost()
            .body(jsonBody)
            .header("Content-Type" to "application/json")
            .responseJson { _, _, result ->
                if (result is Result.Success) {
                    val responseJson = result.get().obj()
                    val paymentIntentClientSecret = responseJson.getString("paymentIntent")
                    val customerConfig = PaymentSheet.CustomerConfiguration(
                        responseJson.getString("customer"),
                        responseJson.getString("ephemeralKey")
                    )
                    val publishableKey = responseJson.getString("publishableKey")
                    PaymentConfiguration.init(this, publishableKey)

                    paymentSheet.presentWithPaymentIntent(
                        paymentIntentClientSecret,
                        PaymentSheet.Configuration(
                            merchantDisplayName = "John Doe",
                            customer = customerConfig,
                            allowsDelayedPaymentMethods = true
                        )
                    )
                }
            }
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show()
            }

            is PaymentSheetResult.Failed -> {
                Toast.makeText(
                    this,
                    "Payment error: ${paymentSheetResult.error}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            is PaymentSheetResult.Completed -> {
                Toast.makeText(this, "Payment completed", Toast.LENGTH_SHORT).show()
            }
        }

        paymentButton.isEnabled = true
    }


}