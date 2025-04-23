package com.ecliptacare.backend.routes.requests.products

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class PatchProductRequest(
    val name: String? = null,
    val description: String? = null,
    val categoryId: Int? = null,
    @Contextual val price: BigDecimal? = null,
)
