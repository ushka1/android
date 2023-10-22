package com.example.routes

import com.example.dao.productDao
import com.example.models.Product
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.productRouting() {
    route("/product") {
        get {
            call.respond(productDao.allProducts())
        }
        get("{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val product = productDao.product(id) ?: return@get call.respondText(
                "No product with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(product)
        }
        post {
            val productData = call.receive<Product>()
            productDao.addNewProduct(
                productData.name!!,
                productData.description!!,
                productData.price!!,
                productData.categoryCode!!,
            )

            call.respondText(
                "Product stored correctly",
                status = HttpStatusCode.OK
            )
        }
        put("{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@put call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            val productData = call.receive<Product>()
            val res = productDao.editProduct(
                id,
                productData.name,
                productData.description,
                productData.price,
                productData.categoryCode,
            )

            if (res) {
                call.respondText(
                    "Product updated correctly",
                    status = HttpStatusCode.OK
                )
            } else {
                call.respondText(
                    "Product not found",
                    status = HttpStatusCode.NotFound
                )
            }

        }
        delete("{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (productDao.deleteProduct(id)) {
                call.respondText("Product removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}