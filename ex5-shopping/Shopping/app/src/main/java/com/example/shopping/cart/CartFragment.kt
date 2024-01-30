package com.example.shopping.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.R

class CartFragment(private val cartViewModel: CartViewModel) : Fragment(), CartItemListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        val cartRecyclerView = view.findViewById<RecyclerView>(R.id.cart_recycler_view)
        val cartLayoutManger = LinearLayoutManager(context)
        cartRecyclerView.layoutManager = cartLayoutManger

        val adapter = CartAdapter(cartViewModel.cartItems, this)
        cartRecyclerView.adapter = adapter

        return view
    }

    override fun onProductQuantityChanged(productId: String, quantity: Int) {
        cartViewModel.updateProductQuantity(productId, quantity)
    }

}