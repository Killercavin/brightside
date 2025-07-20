package com.brightside.routes.users.admin

import com.brightside.utils.enum.AdminRole
import kotlinx.serialization.Serializable

@Serializable
data class AdminSession(
    val adminId: Int,
    val email: String,
    val role: AdminRole  // Optional enhancement for future role-based features
)
