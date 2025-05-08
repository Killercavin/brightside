package com.brightside.backend.routes.requests.cart

import kotlinx.serialization.Serializable

@Serializable
data class AddToCartRequest(
    val productId: Int,
    val quantity: Int = 1, // defaults the quantity to one if not specified
)
