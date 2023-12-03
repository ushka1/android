package com.example.shopping.cart;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.R

public class CartAdapter(
    private val cartItems: LiveData<List<Cart.CartItem>>,
    private val itemListener: CartItemListener
) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    init {
        cartItems.observeForever { newCartItems ->
            newCartItems?.let {
                notifyDataSetChanged()
            }
        }
    }

    class CartViewHolder(cartView: View) : RecyclerView.ViewHolder(cartView) {
        val productNameView: TextView = cartView.findViewById(R.id.product_name)
        val totalPriceView: TextView = cartView.findViewById(R.id.total_price)
        val quantityView: EditText = cartView.findViewById(R.id.product_quantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_cart, parent, false
        )
        return CartAdapter.CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartItems.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems.value?.get(position)
        if (item != null) {
            holder.productNameView.text = item.product.name

            val formattedPrice = String.format("%.2f", item.product.price * item.quantity)
            holder.totalPriceView.text = "$formattedPrice$"

            holder.quantityView.setText(item.quantity.toString())
//            val text = s.toString()
//            val quantity: Int = try {
//                text.toInt()
//            } catch (e: NumberFormatException) {
//                0
//            }
//            itemListener.onProductQuantityChanged(item.product.id, quantity)
        }
    }

}
