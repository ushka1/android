package com.example.netshopping.category

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.netshopping.R

@SuppressLint("NotifyDataSetChanged")
class CategoryAdapter(
    private val categories: LiveData<List<Category>>,
    private val selectedCategoryId: LiveData<String?>,
    private val listener: CategoryItemListener
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    init {
        categories.observeForever { newItems ->
            newItems?.let {
                notifyDataSetChanged()
            }
        }
        selectedCategoryId.observeForever {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = categories.value?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return categories.value?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.findViewById<TextView>(R.id.category_name)

        fun bind(item: Category) {
            textView.text = item.name
            textView.setOnClickListener {
                if (item.id != null) {
                    listener.onCategorySelect(item.id!!)
                }
            }

            if (item.id == selectedCategoryId.value) {
                textView.setTypeface(null, Typeface.BOLD)
            } else {
                textView.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

}