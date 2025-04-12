package com.brightside.backend.routes

import com.brightside.backend.controllers.CategoryController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoryRoutes() {
    route("/categories") {
        // get all categories
        get {
            try {
                val categories = CategoryController.getAllCategories()
                if (categories.isNotEmpty()) {
                    call.respond(HttpStatusCode.OK, categories)
                } else {
                    call.respond(HttpStatusCode.NoContent, "No categories found")
                }
            } catch (e: Exception) {
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }

        // get products by category id
        get("/{id}"){
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                    return@get
                }

                val productsByCategory = CategoryController.getProductsByCategoryId(id)
                if (productsByCategory.isNotEmpty()) {
                    call.respond(HttpStatusCode.OK, productsByCategory)
                } else{
                    call.respond(HttpStatusCode.NotFound, "Products under category id $id not found")
                }
            } catch (e: Exception) {
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }
    }
}