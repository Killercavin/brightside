package com.brightside.backend.models

import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.Instant

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val price: BigDecimal,
    val description: String,
    val category: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
