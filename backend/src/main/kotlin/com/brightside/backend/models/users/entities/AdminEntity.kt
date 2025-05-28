package com.brightside.backend.models.users.entities

import java.time.Instant

// internal logic representation
data class AdminEntity(
    val id: Int,
    val name: String,
    val email: String,
    val passwordHash: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)