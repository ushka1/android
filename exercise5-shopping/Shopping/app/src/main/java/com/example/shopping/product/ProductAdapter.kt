package com.example.shopping.product

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.ProductDetailsActivity
import com.example.shopping.R


class ProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.product_card, parent, false
        )
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.productView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductDetailsActivity::class.java)
            intent.putExtra("productId", product.id)
            holder.itemView.context.startActivity(intent)
        }
        holder.nameView.text = product.name
        holder.authorView.text = "by ${product.author}"
        holder.imageView.setImageResource(product.imageResourceId)
        holder.priceView.text = "${product.price}$"
        holder.addToCartButton.setOnClickListener {
            println("ADD TO CART: ${product.name}")
        }
    }

    class ProductViewHolder(productView: View) : RecyclerView.ViewHolder(productView) {
        val productView: View = productView
        val nameView: TextView = productView.findViewById(R.id.product_name)
        val authorView: TextView = productView.findViewById(R.id.product_author)
        val imageView: ImageView = productView.findViewById(R.id.product_image)
        val priceView: TextView = productView.findViewById(R.id.product_price)
        val addToCartButton: Button = productView.findViewById(R.id.add_to_cart_button)
    }

}


