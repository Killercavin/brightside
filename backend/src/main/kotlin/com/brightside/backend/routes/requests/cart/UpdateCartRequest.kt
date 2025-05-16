package com.brightside.backend.routes.requests.cart

import kotlinx.serialization.Serializable

// Used by the client (request body)
@Serializable
data class QuantityUpdateRequest(val quantity: Int)

// Internal DTO used by the service layer
@Serializable
data class UpdateCartRequest(val productId: Int, val quantity: Int)

