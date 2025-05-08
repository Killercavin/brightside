package com.brightside.backend.routes

import com.brightside.backend.controllers.CategoryController
import com.brightside.backend.routes.requests.categories.AddCategoryRequest
import com.brightside.backend.routes.requests.categories.PatchCategoryRequest
import com.brightside.backend.routes.requests.categories.UpdateCategoryRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoryRoutes() {
    route("/api/categories") {
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

        // get category by id
        get("/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                    return@get
                }

                val categoryById = CategoryController.getCategoryById(id)
                if (categoryById != null) {
                    call.respond(HttpStatusCode.OK, categoryById)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Category with id $id not found")
                }
            } catch (e: Exception) {
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }

        // get products by category id
        get("/products/{id}"){
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

        // add category
        post {
            try {
                val request = call.receive<AddCategoryRequest>()
                val response = CategoryController.addCategory(request)

                if (response != null) {
                    call.respond(HttpStatusCode.Created, response)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Error adding category")
                }
            } catch (e: Exception){
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }

        // update category
        put("/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()

                if (id != null) {
                    val request = call.receive<UpdateCategoryRequest>()
                    val category = CategoryController.updateCategory(id, request)
                    if (category != null) {
                        call.respond(HttpStatusCode.OK, category)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Category with id $id not found")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Error updating category")
                }
            } catch (e: Exception){
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }

        // patch category
        patch("/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()

                if (id != null) {
                    val request = call.receive<PatchCategoryRequest>()
                    val category = CategoryController.patchCategory(id, request)
                    if (category != null) {
                        call.respond(HttpStatusCode.OK, category)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Category with id $id not found")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Error patching category")
                }
            } catch (e: Exception){
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }

        // delete category
        delete("/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id != null) {
                    val deleteCategory = CategoryController.deleteCategory(id)
                    if (deleteCategory) {
                        call.respond(HttpStatusCode.NoContent)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Category with id $id not found")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                }
            } catch (e: Exception){
                call.application.log.error("Error processing request", e)
                call.respond(HttpStatusCode.BadRequest, "Error processing request: ${e.message}")
            }
        }
    }
}