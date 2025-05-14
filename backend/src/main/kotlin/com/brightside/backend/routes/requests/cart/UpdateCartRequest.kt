package com.brightside.backend.routes.requests.cart

import kotlinx.serialization.Serializable

@Serializable
data class UpdateCartRequest(
    val productId: Int,
    val quantity: Int
)
