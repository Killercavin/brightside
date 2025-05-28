package com.brightside.backend.models.products.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

// defines how the image is represented in API responses or data transfer using Kotlin data classes
@Serializable
data class ProductImageDTO(
    val id: Int,
    val productId: Int?,          // IDs only, not reference declarations
    val variantId: Int?,
    val url: String,
    val altText: String?,
    @Contextual val createdAt: Instant,
    @Contextual val updatedAt: Instant
)
