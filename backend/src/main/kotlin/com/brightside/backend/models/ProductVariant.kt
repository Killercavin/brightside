package com.brightside.backend.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.Instant

@Serializable
data class ProductVariant(
    val id: Int,
    val sku: String,
    val color: String?,
    val size: String?,
    val stockQuantity: Int,
    @Contextual val price: BigDecimal?,
    @Contextual val createdAt: Instant,
    @Contextual val updatedAt: Instant
)
