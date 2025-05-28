package com.brightside.backend.models.users.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class AdminDTO(
    @Contextual val id: Int,
    val name: String,
    val email: String,
    @Contextual val createdAt: Instant,
    @Contextual val updatedAt: Instant,
)