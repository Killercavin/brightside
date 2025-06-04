package com.brightside.backend.models.users.admin.entities

import kotlinx.serialization.Serializable
import java.time.Instant

// internal logic representation
@Serializable
data class AdminEntity(
    val id: Int,
    val name: String,
    val email: String,
    val passwordHash: String,
    val createdAt: String,
    val updatedAt: String
)