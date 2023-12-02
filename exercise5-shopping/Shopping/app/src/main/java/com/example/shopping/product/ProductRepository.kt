package com.example.shopping.product

import com.example.shopping.R

class ProductRepository private constructor() {

    companion object {
        @Volatile
        private var instance: ProductRepository? = null
        fun getInstance(): ProductRepository {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = ProductRepository()
                    }
                }
            }
            return instance!!
        }
    }

    private val products: MutableList<Product> = mutableListOf(
        Product(
            name = "Celestial Harmony Canvas",
            price = 2499.99,
            author = "Aurora Starlight",
            description = "A breathtaking canvas depicting the harmony of celestial bodies in an otherworldly landscape",
            imageResourceId = R.drawable.celestial_harmony_painting_image
        ),
        Product(
            name = "Ethereal Dreamscape Artwork",
            price = 2899.99,
            author = "Luna Serenity",
            description = "An ethereal artwork that transports viewers into a dreamscape of vibrant colors and surreal imagery",
            imageResourceId = R.drawable.ethereal_dreamscape_painting_image
        ),
        Product(
            name = "Whimsical Wonderland Illustration",
            price = 2199.99,
            author = "Mystic Enchantress",
            description = "An illustration capturing the whimsy and magic of an enchanting wonderland",
            imageResourceId = R.drawable.whimsical_wonderland_painting_image
        ),
        Product(
            name = "Timeless Abstract Expressionism",
            price = 2699.99,
            author = "Harmony Brushstroke",
            description = "A timeless piece of abstract expressionism, evoking emotions and sparking imagination",
            imageResourceId = R.drawable.timeless_abstract_expressionism_image
        ),
        Product(
            name = "Mystical Forest Landscape",
            price = 2399.99,
            author = "Whispering Woodlands",
            description = "A captivating painting portraying a mystical forest landscape with enchanting creatures",
            imageResourceId = R.drawable.mystical_forest_painting_image
        )
    )

    fun getAllProducts(): List<Product> {
        return products
    }

    fun getProductById(id: String): Product? {
        return products.find { it.id == id }
    }

}