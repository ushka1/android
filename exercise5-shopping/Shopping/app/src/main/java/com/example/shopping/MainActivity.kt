package com.example.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.shopping.cart.CartViewModel
import com.example.shopping.main.MainPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private val cartViewModel = CartViewModel()

    override fun onResume() {
        super.onResume()
        cartViewModel.reloadItems()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager = findViewById(R.id.main_view_pager)
        val tabLayout: TabLayout = findViewById(R.id.main_tab_layout)

        val adapter = MainPagerAdapter(supportFragmentManager, cartViewModel)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
    }

}