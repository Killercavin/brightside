package com.brightside.models.users.admin.dto.requests

import com.brightside.utils.emum.AdminRole
import kotlinx.serialization.Serializable

@Serializable
data class AdminCreateRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val role: AdminRole? = null  // Optional, can be null
)
