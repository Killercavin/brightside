package com.brightside.models.users.admin.dto.requests

import com.brightside.utils.enum.AdminRole
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAdminRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val password: String? = null,
    val role: AdminRole? = null // maybe only super admin can change this
)
