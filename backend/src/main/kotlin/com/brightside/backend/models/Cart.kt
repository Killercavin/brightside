package com.brightside.backend.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class CartItem(val productId: Int, var quantity: Int)

@Serializable
data class CartSession(var items: MutableList<CartItem> = mutableListOf())

@Serializable
data class CartProduct(
    @Contextual val productId: Int,
    val quantity: Int,
    val name: String,
    val price: Double,
)

@Serializable
data class Cart(
    val items: List<CartProduct>,
    val totalPrice: Double
)

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)

