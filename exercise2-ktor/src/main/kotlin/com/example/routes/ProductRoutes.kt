package com.example.routes

import com.example.models.Product
import com.example.models.productStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productRouting() {
    route("/product") {
        get {
            if (productStorage.isNotEmpty()) {
                call.respond(productStorage)
            } else {
                call.respondText("No products found!", status = HttpStatusCode.OK)
            }
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val product = productStorage.find { it.id == id } ?: return@get call.respondText(
                "No product with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(product)
        }
        post {
            val product = call.receive<Product>()
            productStorage.add(product)
            call.respondText(
                "Product stored correctly",
                status = HttpStatusCode.OK
            )
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (productStorage.removeIf { it.id == id }) {
                call.respondText("Product removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}