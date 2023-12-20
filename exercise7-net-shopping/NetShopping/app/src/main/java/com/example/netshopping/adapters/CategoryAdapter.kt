package com.example.netshopping.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.netshopping.R
import com.example.netshopping.models.Category

@SuppressLint("NotifyDataSetChanged")
class CategoryAdapter(private val items: LiveData<List<Category>>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

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
            .inflate(R.layout.item_category, parent, false)
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
        private val textView = itemView.findViewById<TextView>(R.id.category_name)

        fun bind(item: Category) {
            textView.text = item.name
        }
    }
}