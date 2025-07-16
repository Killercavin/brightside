package com.brightside.validators.users.admin

import com.brightside.models.users.admin.dto.requests.AdminLoginRequest

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}

object AdminLoginValidator {
    // validation of the admin credentials
    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")

    fun validate(request: AdminLoginRequest): ValidationResult {
        if (request.email.isBlank()) {
            return ValidationResult.Error("Email cannot be blank")
        }

        if (!emailRegex.matches(request.email)) {
            return ValidationResult.Error("Invalid email")
        }

        if (request.password.isBlank()) {
            return ValidationResult.Error("Password cannot be blank")
        }

        if (request.password.length < 6) {
            return ValidationResult.Error("Password must be more than 6 characters")
        }

        return ValidationResult.Success
    }
}

