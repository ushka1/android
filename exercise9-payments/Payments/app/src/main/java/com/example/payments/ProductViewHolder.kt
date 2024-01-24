package com.example.payments

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductViewHolder(
    itemView: View,
    private val viewModel: ProductViewModel
) : RecyclerView.ViewHolder(itemView) {

    private val productName: TextView = itemView.findViewById(R.id.product_name)
    private val productPrice: TextView = itemView.findViewById(R.id.product_price)
    private val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
    private val increaseButton: Button = itemView.findViewById(R.id.add_button)
    private val decreaseButton: Button = itemView.findViewById(R.id.remove_button)

    fun bind(product: Product) {
        productName.text = product.name
        productPrice.text = "${product.price / 100}.${product.price % 100} PLN"
        productQuantity.text = "Quantity ${product.quantity.toString()}"

        increaseButton.setOnClickListener {
            viewModel.increaseQuantity(product)
        }

        decreaseButton.setOnClickListener {
            viewModel.decreaseQuantity(product)
        }

        decreaseButton.isEnabled = product.quantity > 0
    }
}