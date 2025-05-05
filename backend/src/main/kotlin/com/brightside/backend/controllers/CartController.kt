package com.brightside.backend.controllers

import com.brightside.backend.models.ApiResponse
import com.brightside.backend.models.Cart
import com.brightside.backend.models.CartSession
import com.brightside.backend.services.CartService
import io.ktor.server.application.*
import io.ktor.server.sessions.*

class CartController(private val cartService: CartService) {

    suspend fun getCart(call: ApplicationCall): ApiResponse<Cart> {
        val session = call.sessions.get<CartSession>() ?: CartSession()
        val cart = cartService.getCart(session)

        return ApiResponse(
            success = true,
            message = if (cart.items.isEmpty()) "Your cart is empty" else "Cart retrieved successfully",
            data = cart
        )
    }
}
