package com.example.shopping.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.R
import com.example.shopping.cart.CartViewModel

class ProductListFragment(
    private val cartViewModel: CartViewModel
) : Fragment(), ProductItemListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)

        val productRecyclerView = view.findViewById<RecyclerView>(R.id.product_recycler_view)
        val productLayoutManger = LinearLayoutManager(context)
        productRecyclerView.layoutManager = productLayoutManger

        val productList = ProductRepository.getInstance().getAllProducts()
        val adapter = ProductAdapter(productList, this)
        productRecyclerView.adapter = adapter

//        val dividerDrawable = ContextCompat.getDrawable(view.context, R.drawable.line_divider)
//        val dividerItemDecoration = DividerItemDecoration(
//            productRecyclerView.context,
//            productLayoutManger.orientation
//        )
//        if (dividerDrawable != null) {
//            dividerItemDecoration.setDrawable(dividerDrawable)
//        }
//        productRecyclerView.addItemDecoration(dividerItemDecoration, -1)

        return view
    }

    override fun onProductAddedToCart(productId: String) {
        cartViewModel.addProductToCart(productId)
    }

}