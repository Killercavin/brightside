package com.brightside.models.users.admin.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class AdminErrorResponse(
    val error: String,
    val code: AdminErrorCode,
    val timestamp: Long = System.currentTimeMillis()
)

// Create specific error codes
@Serializable
enum class AdminErrorCode {
    VALIDATION_ERROR,
    INVALID_REQUEST,
    INVALID_CREDENTIALS,
    ACCOUNT_DISABLED,
    INTERNAL_ERROR,
    INTERNAL_SERVER_ERROR,
    UNAUTHORIZED,
    FORBIDDEN,
    ADMIN_NOT_FOUND
}
