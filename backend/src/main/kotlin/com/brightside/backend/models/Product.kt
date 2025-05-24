package com.brightside.backend.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.EntityID
import java.time.Instant

@Serializable
data class Product(
    @Contextual val id: EntityID<Int>,
    val name: String,
    val description: String,
    val category: String,
    val categoryId: Int?,
    @Contextual val createdAt: Instant,
    @Contextual val updatedAt: Instant,
    val variants: List<ProductVariant> = emptyList(), // <-- added variants for stock manipulation
    val images: List<ProductImage> = emptyList() // <-- added
)

