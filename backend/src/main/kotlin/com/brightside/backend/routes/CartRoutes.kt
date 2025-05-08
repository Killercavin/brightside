package com.brightside.backend.routes

import com.brightside.backend.controllers.CartController
import com.brightside.backend.models.ApiResponse
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
                call.respond(HttpStatusCode.InternalServerError, ApiResponse<Unit>(
                    success = false,
                    message = "Unable to add product to cart",
                    data = null
                ))
            }
        }
    }
}
