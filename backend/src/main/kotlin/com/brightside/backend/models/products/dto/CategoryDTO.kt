package com.brightside.backend.models.products.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class CategoryDTO(
    @Contextual val id: Int,
    val name: String,
    val description: String,
    @Contextual val createdAt: Instant,
    @Contextual val updatedAt: Instant,
)
