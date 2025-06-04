package com.brightside.backend.models.users.admin.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class AdminProfileResponse(
    val id: Int,
    val email: String,
    val name: String?,
    val createdAt: String,
)