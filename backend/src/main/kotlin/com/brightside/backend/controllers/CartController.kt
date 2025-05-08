package com.brightside.backend.controllers

import com.brightside.backend.models.ApiResponse
import com.brightside.backend.models.Cart
import com.brightside.backend.models.CartSession
import com.brightside.backend.routes.requests.cart.AddToCartRequest
import com.brightside.backend.services.CartService
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.sessions.*

class CartController(private val cartService: CartService) {

    // get the cart Item
    suspend fun getCart(call: ApplicationCall): ApiResponse<Cart> {
        val session = call.sessions.get<CartSession>() ?: CartSession()
        val cart = cartService.getCart(session)

        return ApiResponse(
            success = true,
            message = if (cart.items.isEmpty()) "Your cart is empty" else "Cart retrieved successfully",
            data = cart
        )
    }

    // add item to the cart
    suspend fun addToCart(call: ApplicationCall): ApiResponse<Cart> {
        val session = call.sessions.get<CartSession>() ?: CartSession()
        val request = call.receive<AddToCartRequest>() // request body from add request data class

        // return a boolean
        val success = cartService.addToCart(session, productId = request.productId, quantity = request.quantity)

        /**
         * If product not found, return error response
         */
        if (!success) {
            return ApiResponse(
                success = false,
                message = "Product not found",
                data = null
            )
        }

        // Save the updated session back
        call.sessions.set(session)

        // Get the updated cart to return in the response
        val updatedCart = cartService.getCart(session)

        return ApiResponse(
            success = true,
            message = "Product added successfully",
            data = updatedCart
        )
    }
}
