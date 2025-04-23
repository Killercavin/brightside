package com.ecliptacare.backend.routes.requests.products

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class AddProductRequest(
    val name: String,
    val description: String,
    val categoryId: Int,
    @Contextual val price: BigDecimal  // Add @Contextual here
)