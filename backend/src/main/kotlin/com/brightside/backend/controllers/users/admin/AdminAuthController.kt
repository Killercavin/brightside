package com.brightside.backend.controllers.users.admin

import com.brightside.backend.models.users.admin.dto.requests.AdminLoginRequest
import com.brightside.backend.models.users.admin.dto.requests.AdminRefreshTokenRequest
import com.brightside.backend.models.users.admin.dto.responses.AdminErrorResponse
import com.brightside.backend.models.users.admin.dto.responses.ErrorCodes
import com.brightside.backend.services.users.admin.auth.AdminAuthService
import com.brightside.backend.validators.users.admin.AdminLoginValidator
import com.brightside.backend.validators.users.admin.ValidationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*

/**
 * Specialized controller for admin authentication operations
 * Handles: login, logout, token refresh, password reset
 */

object AdminAuthController {

    suspend fun login(call: ApplicationCall) {
        try {
            val request = parseAndValidateLoginRequest(call)
            val result = AdminAuthService.login(request)

            result.onSuccess { response ->
                call.respond(HttpStatusCode.OK, response)
            }.onFailure { error ->
                handleAuthError(call, error)
            }

        } catch (e: BadRequestException) {
            call.respond(
                HttpStatusCode.BadRequest,
                AdminErrorResponse("Invalid request format", ErrorCodes.INVALID_REQUEST)
            )
        } catch (e: ValidationException) {
            call.respond(
                HttpStatusCode.BadRequest,
                AdminErrorResponse(e.message ?: "Validation failed", ErrorCodes.VALIDATION_ERROR)
            )
        } catch (e: Exception) {
            call.application.log.error("Unexpected error in admin login", e)
            call.respond(
                HttpStatusCode.InternalServerError,
                AdminErrorResponse("An unexpected error occurred", ErrorCodes.INTERNAL_ERROR)
            )
        }
    }

    suspend fun refreshToken(call: ApplicationCall) {
        try {
            val request = call.receive<AdminRefreshTokenRequest>()
            val result = AdminAuthService.refreshToken(request.refreshToken)
            result.onSuccess { response ->
                call.respond(HttpStatusCode.OK, response)
            }.onFailure { error ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    AdminErrorResponse("Invalid refresh token", ErrorCodes.INVALID_CREDENTIALS)
                )
            }

        } catch (e: Exception) {
            call.application.log.error("Error refreshing admin token", e)
            call.respond(
                HttpStatusCode.InternalServerError,
                AdminErrorResponse("Token refresh failed", ErrorCodes.SERVICE_ERROR)
            )
        }
    }

    private suspend fun parseAndValidateLoginRequest(call: ApplicationCall): AdminLoginRequest {
        val request = try {
            call.receive<AdminLoginRequest>()
        } catch (e: Exception) {
            throw BadRequestException("Invalid JSON format")
        }

        when (val validationResult = AdminLoginValidator.validate(request)) {
            is ValidationResult.Success -> return request
            is ValidationResult.Error -> throw ValidationException(validationResult.message)
        }
    }

    private suspend fun handleAuthError(call: ApplicationCall, error: Throwable) {
        when (error) {
            is SecurityException -> {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    AdminErrorResponse("Invalid credentials", ErrorCodes.INVALID_CREDENTIALS)
                )
            }
            is IllegalStateException -> {
                call.respond(
                    HttpStatusCode.Forbidden,
                    AdminErrorResponse("Account is disabled or locked", ErrorCodes.ACCOUNT_DISABLED)
                )
            }
            else -> {
                call.application.log.error("Auth service error", error)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    AdminErrorResponse("Authentication service unavailable", ErrorCodes.SERVICE_ERROR)
                )
            }
        }
    }
}

class ValidationException(message: String) : Exception(message)