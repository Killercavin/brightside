package com.brightside.backend.routes

import com.brightside.backend.controllers.ProductController
import com.brightside.backend.routes.requests.products.PatchProductRequest
import com.brightside.backend.routes.requests.products.UpdateProductRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productRoutes() {
    route("/api/products") {

        // get products
        get {
            try {
                val products = ProductController.getAllProducts()
                if (products.isNotEmpty()) {
                    call.respond(HttpStatusCode.OK, products)  // Automatically serialized to JSON
                } else {
                    call.respond(HttpStatusCode.NoContent, "No products found")
                }
            } catch (e: Exception) {
                // Added this for debugging
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }

        // get product by id
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
                // Added this for debugging
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }

        // add product
        post {
            try {
                val response = ProductController.addProduct(call)
                call.respond(HttpStatusCode.Created, response)
            } catch (e: Exception) {
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }

        // update product
        put("/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id != null) {
                    val request = call.receive<UpdateProductRequest>()
                    val product = ProductController.updateProduct(id, request)
                    if (product != null) {
                        call.respond(HttpStatusCode.OK, product)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Product with id $id not found")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                }
            } catch (e: Exception) {
                // Added this for debugging
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }

        // patch product
        patch("/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id != null) {
                    val request = call.receive<PatchProductRequest>()
                    val response = ProductController.patchProduct(id, request)
                    if (response != null) {
                        call.respond(HttpStatusCode.OK, response)
                    } else{
                        call.respond(HttpStatusCode.NotFound, "Product with id $id not found")
                    }
                } else{
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                }
            } catch (e: Exception) {
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }

        // delete product
        delete("/{id}") {
            try {
                delete("/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id != null) {
                        val deletedProduct = ProductController.deleteProduct(id)
                        if (deletedProduct) {
                            call.respond(HttpStatusCode.NoContent)
                        } else {
                            call.respond(HttpStatusCode.NotFound, "Product with id $id not found")
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                    }
                }
            } catch (e: Exception) {
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }

    }
}