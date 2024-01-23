package com.example.payments

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult

class MainActivity : AppCompatActivity() {
    private lateinit var paymentSheet: PaymentSheet
    private lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    private lateinit var paymentIntentClientSecret: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        "http://10.0.2.2:3000/payment-sheet".httpPost().responseJson { _, _, result ->
            if (result is Result.Success) {
                val responseJson = result.get().obj()
                paymentIntentClientSecret = responseJson.getString("paymentIntent")
                customerConfig = PaymentSheet.CustomerConfiguration(
                    responseJson.getString("customer"),
                    responseJson.getString("ephemeralKey")
                )
                val publishableKey = responseJson.getString("publishableKey")
                PaymentConfiguration.init(this, publishableKey)
            }
        }

        val payButton = findViewById<Button>(R.id.pay_button)
        payButton.setOnClickListener {
            presentPaymentSheet()
        }
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                print("Canceled")
            }

            is PaymentSheetResult.Failed -> {
                print("Error: ${paymentSheetResult.error}")
            }

            is PaymentSheetResult.Completed -> {
                print("Completed")
            }
        }
    }

    private fun presentPaymentSheet() {
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