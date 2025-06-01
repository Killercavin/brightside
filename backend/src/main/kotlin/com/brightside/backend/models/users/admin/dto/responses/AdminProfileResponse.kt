package com.brightside.backend.models.users.admin.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class AdminProfileResponse(
    val id: Int,
    val email: String,
    val fullName: String?,
    val createdAt: String,
)