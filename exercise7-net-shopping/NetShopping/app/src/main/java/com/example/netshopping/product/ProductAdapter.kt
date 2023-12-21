package com.example.netshopping.product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.netshopping.R

@SuppressLint("NotifyDataSetChanged")
class ProductAdapter(private val items: LiveData<List<Product>>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    init {
        items.observeForever { newItems ->
            newItems?.let {
                notifyDataSetChanged()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.value?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return items.value?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.product_name)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.product_description)
        private val priceTextView: TextView = itemView.findViewById(R.id.product_price)

        fun bind(item: Product) {
            nameTextView.text = item.name
            descriptionTextView.text = item.description
            priceTextView.text = item.price.toString() + "$"
        }
    }

}