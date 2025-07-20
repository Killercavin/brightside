package com.brightside.models.users.admin.dto.requests

import com.brightside.utils.enum.AdminRole
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAdminRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val password: String? = null,
    /**
     * The role of the admin. This property determines the administrative privileges of the user.
     * 
     * Business Rule: Only a super admin is allowed to change this property. Other roles do not
     * have the necessary permissions to modify it.
     */
    val role: AdminRole? = null
)
