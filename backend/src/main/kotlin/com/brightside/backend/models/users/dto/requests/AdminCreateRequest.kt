package com.brightside.backend.models.users.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class AdminCreateRequest(
    val name: String,
    val email: String,
    val password: String
)
