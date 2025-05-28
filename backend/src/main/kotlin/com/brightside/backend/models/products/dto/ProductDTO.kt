package com.brightside.backend.models.products.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class ProductDTO(
    @Contextual val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val categoryId: Int?,
    @Contextual val createdAt: Instant,
    @Contextual val updatedAt: Instant,
    val variants: List<ProductVariantDTO> = emptyList(), // <-- added variants for stock manipulation
    val images: List<ProductImageDTO> = emptyList() // <-- added
)