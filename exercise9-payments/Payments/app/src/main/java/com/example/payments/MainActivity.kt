package com.example.payments

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {
    private lateinit var paymentSheet: PaymentSheet
    private lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    private lateinit var paymentIntentClientSecret: String
    private lateinit var paymentButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)

        paymentButton = findViewById<Button>(R.id.payment_button)
        paymentButton.setOnClickListener {
            paymentButton.isEnabled = false
            startPayment()
        }
    }


    private fun startPayment() {
        val productList = ProductList(listOf(Product("Bike", 10000, 3)))
        val jsonBody = Json.encodeToString(ProductList.serializer(), productList)

        "http://10.0.2.2:3000/payment-sheet"
            .httpPost()
            .body(jsonBody)
            .header("Content-Type" to "application/json")
            .responseJson { _, _, result ->
                if (result is Result.Success) {
                    val responseJson = result.get().obj()
                    paymentIntentClientSecret = responseJson.getString("paymentIntent")
                    customerConfig = PaymentSheet.CustomerConfiguration(
                        responseJson.getString("customer"),
                        responseJson.getString("ephemeralKey")
                    )
                    val publishableKey = responseJson.getString("publishableKey")
                    PaymentConfiguration.init(this, publishableKey)

                    paymentSheet.presentWithPaymentIntent(
                        paymentIntentClientSecret,
                        PaymentSheet.Configuration(
                            merchantDisplayName = "My merchant name",
                            customer = customerConfig,
                            allowsDelayedPaymentMethods = true
                        )
                    )
                }
            }
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        paymentButton.isEnabled = true

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
    }


}