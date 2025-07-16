package com.brightside.models.users.admin.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class AdminLoginRequest(
    val email: String,
    val password: String
)
