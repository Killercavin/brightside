package com.brightside.backend.routes.requests

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class UpdateProductRequest(
    val name: String,
    val description: String,
    val categoryId: Int,
    @Contextual val price: BigDecimal,
)
