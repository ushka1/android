package com.example.routes

import com.example.dao.categoryDao
import com.example.models.Category
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoryRouting() {
    route("/category") {
        get {
            call.respond(categoryDao.allCategories())
        }
        get("{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val category = categoryDao.category(id) ?: return@get call.respondText(
                "No category with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(category)
        }
        post {
            val categoryData = call.receive<Category>()
            categoryDao.addNewCategory(
                categoryData.name,
            )

            call.respondText(
                "Category stored correctly",
                status = HttpStatusCode.OK
            )
        }
        put("{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@put call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            val categoryData = call.receive<Category>()
            val res = categoryDao.editCategory(
                id,
                categoryData.name,
            )

            if (res) {
                call.respondText(
                    "Category updated correctly",
                    status = HttpStatusCode.OK
                )
            } else {
                call.respondText(
                    "Category not found",
                    status = HttpStatusCode.NotFound
                )
            }

        }
        delete("{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (categoryDao.deleteCategory(id)) {
                call.respondText("Category removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}