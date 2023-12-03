package com.example.shopping.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.shopping.R

class ProductDetailsFragment : Fragment() {

    companion object {
        fun newInstance(productId: String): ProductDetailsFragment {
            val fragment = ProductDetailsFragment()
            val args = Bundle()
            args.putString("productId", productId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = arguments?.getString("productId")
        if (productId != null) {
            val product = ProductRepository.getInstance().getProductById(productId)
            if (product != null) {
                val nameView: TextView = view.findViewById(R.id.product_name)
                nameView.text = product.name

                val authorView: TextView = view.findViewById(R.id.product_author)
                authorView.text = "by ${product.author}"

                val imageView: ImageView = view.findViewById(R.id.product_image)
                val drawable = ContextCompat.getDrawable(view.context, product.imageResourceId)
                imageView.setImageDrawable(drawable)

                val priceView: TextView = view.findViewById(R.id.product_price)
                priceView.text = "${product.price}$"

                val descriptionView: TextView = view.findViewById(R.id.product_description)
                descriptionView.text = product.description

                val addToCartButton: Button = view.findViewById(R.id.add_to_cart)
            }
        }
    }

}