package com.brightside.models.users.admin.dto.responses

import com.brightside.utils.enum.AdminRole
import kotlinx.serialization.Serializable

@Serializable
data class AdminProfileResponse(
    val id: Int,
    val fullName: String,
    val email: String,
    val role: AdminRole,
    val createdAt: String,
    val updatedAt: String
)