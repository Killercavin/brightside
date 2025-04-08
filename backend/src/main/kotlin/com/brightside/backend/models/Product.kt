package com.brightside.backend.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.EntityID
import java.math.BigDecimal
import java.time.Instant

@Serializable
data class Product(
    @Contextual val id: EntityID<Int>, // Add @Contextual
    val name: String,
    @Contextual val price: BigDecimal, // Add @Contextual
    val description: String,
    val category: String,
    val categoryId: Int,
    @Contextual val createdAt: Instant, // Add @Contextual
    @Contextual val updatedAt: Instant, // Add @Contextual
)