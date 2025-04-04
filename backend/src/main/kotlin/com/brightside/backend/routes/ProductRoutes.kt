package com.brightside.backend.routes

import com.brightside.backend.controllers.ProductController
import com.brightside.backend.models.ProductTable
import com.brightside.backend.models.ProductTable.id
import com.brightside.backend.routes.requests.AddProductRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productRoutes() {
    route("/products") {

        // get all products
        get {
            try {
                val products = ProductController.getAllProducts()
                if (products.isNotEmpty()) {
                    call.respond(products)  // Automatically serialized to JSON
                } else {
                    call.respond(HttpStatusCode.NoContent, "No products found")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "An error occurred: ${e.message}")
            }
        }

        // get a product by id
        get("/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                    return@get
                }

                val productById = ProductController.getProductById(id)
                if (productById != null) {
                    call.respond(HttpStatusCode.OK, productById)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Product with id $id not found")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "An error occurred: ${e.message}")
            }
        }

        // add a product
        post {
            try {
                val request = call.receive<AddProductRequest>()
                val response = ProductController.addProduct(request)

                if (response != null) {
                    call.respond(HttpStatusCode.Created, response)
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to add product")
                }
            } catch (e: Exception) {
                // Add this for debugging
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }
    }
}