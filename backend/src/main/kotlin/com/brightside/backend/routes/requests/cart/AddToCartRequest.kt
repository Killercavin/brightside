package com.brightside.backend.routes.requests.cart

data class AddToCartRequest(
    val productId: Int,
    val quantity: Int,
)
