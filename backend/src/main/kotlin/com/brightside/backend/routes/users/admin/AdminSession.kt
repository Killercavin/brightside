package com.brightside.backend.routes.users.admin

import kotlinx.serialization.Serializable

@Serializable
data class AdminSession(
    val adminId: Int,
    val email: String,
)
