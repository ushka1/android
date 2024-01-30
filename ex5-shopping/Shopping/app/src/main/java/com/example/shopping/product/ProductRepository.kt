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
            description = "This mesmerizing canvas captures the celestial harmony of otherworldly bodies in a breathtaking landscape. The interplay of cosmic elements is masterfully depicted, offering a captivating glimpse into the celestial dance beyond our earthly realms.",
            imageResourceId = R.drawable.celestial_harmony_painting_image
        ),
        Product(
            name = "Ethereal Dreamscape Artwork",
            price = 2899.99,
            author = "Luna Serenity",
            description = "Immerse yourself in an ethereal dreamscape through this remarkable artwork. Luna Serenity's creation weaves a tapestry of vibrant colors and surreal imagery, inviting viewers to transcend the boundaries of reality and explore the realms of the fantastical.",
            imageResourceId = R.drawable.ethereal_dreamscape_painting_image
        ),
        Product(
            name = "Whimsical Wonderland Illustration",
            price = 2199.99,
            author = "Mystic Enchantress",
            description = " Step into a world of enchantment with this whimsical wonderland illustration. Crafted by the hands of the Mystic Enchantress, the artwork captures the magic and charm of an otherworldly realm, where imagination knows no bounds.",
            imageResourceId = R.drawable.whimsical_wonderland_painting_image
        ),
        Product(
            name = "Timeless Abstract Expressionism",
            price = 2699.99,
            author = "Harmony Brushstroke",
            description = "An exploration of timeless beauty, this abstract expressionism piece by Harmony Brushstroke transcends the constraints of time. Evoking emotions and sparking the imagination, it stands as a testament to the enduring power of artistic expression in its purest form.",
            imageResourceId = R.drawable.timeless_abstract_expressionism_image
        ),
        Product(
            name = "Mystical Forest Landscape",
            price = 2399.99,
            author = "Whispering Woodlands",
            description = "Venture into a mystical forest with this captivating painting by Whispering Woodlands. The artwork not only portrays the natural beauty of the woodland landscape but also introduces enchanting creatures that add an extra layer of magic to the scene.",
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