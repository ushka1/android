package com.example.shopping.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.shopping.cart.CartFragment
import com.example.shopping.cart.CartViewModel
import com.example.shopping.product.ProductListFragment

class MainPagerAdapter(
    private val fragmentManager: FragmentManager,
    private val cartViewModel: CartViewModel
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ProductListFragment(cartViewModel)
            1 -> CartFragment(cartViewModel)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Product List"
            1 -> "Cart"
            else -> null
        }
    }

}