package com.brightside.backend.routes

import com.brightside.backend.controllers.CartController
import com.brightside.backend.models.ApiResponse
import com.brightside.backend.models.Cart
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory

fun Route.cartRoutes(cartController: CartController) {
    val logger = LoggerFactory.getLogger("CartRoutes")

    route("/api/cart") {
        // get the cart items
        get {
            try {
                val response = withContext(Dispatchers.IO) {
                    cartController.getCart(call)
                }
                call.respond(HttpStatusCode.OK, response)

            } catch (e: Exception) {
                logger.error("Error retrieving cart", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ApiResponse<Unit>(
                        success = false,
                        message = "Unable to retrieve cart at this time"
                    )
                )
            }
        }

        // post to the cart
        post {
            try {
                val response = withContext(Dispatchers.IO) {
                    cartController.addToCart(call)
                }
                call.respond(HttpStatusCode.Created, response)
            } catch (e: Exception) {
                logger.error("Error adding cart", e)
                call.respond(
                    HttpStatusCode.InternalServerError, ApiResponse<String>(
                        success = false,
                        message = "Unable to add product to cart",
                        data = null
                    )
                )
            }
        }

        // update a cart item
        patch("/{productId}") {
            val productId = call.parameters["productId"]?.toIntOrNull()
            if (productId == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<String>(false, "Invalid or missing product ID")
                )
                return@patch
            }

            try {
                val result = withContext(Dispatchers.IO) {
                    cartController.updateCart(call, productId)
                }

                val status = if (result.success) HttpStatusCode.OK else HttpStatusCode.BadRequest
                call.respond(status, result)

            } catch (e: Exception) {
                logger.error("Error updating cart", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ApiResponse(false, "Unexpected error while updating cart", null)
                )
            }
        }

        // remove an item from the cart
        delete("/{productId}") {
            val productId = call.parameters["productId"]?.toIntOrNull()
            if (productId == null) {
                call.respond(HttpStatusCode.BadRequest, ApiResponse<Cart>(false, "Invalid or missing product ID"))
                return@delete
            }

            try {
                val result = withContext(Dispatchers.IO) {
                    cartController.removeFromCart(call, productId)
                }
                call.respond(HttpStatusCode.OK, result)
            } catch (e: Exception) {
                logger.error("Error deleting cart", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ApiResponse<Cart>(false, "Failed to remove item from cart", null)
                )
            }
        }

        // clear cart items route
        delete {
            try {
                val result = withContext(Dispatchers.IO) {
                    cartController.clearCart(call)
                }

                val status = if (result.success) HttpStatusCode.OK else HttpStatusCode.BadRequest
                call.respond(status, result)

            } catch (e: Exception) {
                logger.error("Error clearing cart", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ApiResponse<Cart>(false, "Failed to clear cart", null)
                )
            }
        }


    }
}
