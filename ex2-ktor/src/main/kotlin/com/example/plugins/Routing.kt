package com.example.plugins

import com.example.routes.categoryRouting
import com.example.routes.productRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        productRouting()
        categoryRouting()
        get("/") {
            call.respondText("The server is up!")
        }
    }
}
