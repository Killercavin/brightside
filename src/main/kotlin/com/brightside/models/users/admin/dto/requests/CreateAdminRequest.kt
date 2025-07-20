package com.brightside.models.users.admin.dto.requests

import com.brightside.utils.enum.AdminRole
import kotlinx.serialization.Serializable

@Serializable
data class CreateAdminRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val role: AdminRole? = null  // Optional, can be null
)
