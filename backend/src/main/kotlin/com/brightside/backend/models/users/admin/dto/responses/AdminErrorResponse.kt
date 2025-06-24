package com.brightside.backend.models.users.admin.dto.responses

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
    SERVICE_ERROR,
    UNAUTHORIZED,
    FORBIDDEN
}
