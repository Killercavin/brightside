package com.brightside.controllers.users.admin

import com.brightside.exceptions.ValidationException
import com.brightside.extensions.users.admin.respondError
import com.brightside.models.users.admin.dto.requests.AdminLoginRequest
import com.brightside.models.users.admin.dto.requests.RefreshTokenRequest
import com.brightside.models.users.admin.dto.responses.AdminErrorCode
import com.brightside.services.users.admin.auth.AdminAuthService
import com.brightside.validators.users.admin.AdminLoginValidator
import com.brightside.validators.users.admin.ValidationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.respond

class AdminAuthController(
    private val adminAuthService: AdminAuthService
) {

    suspend fun adminLogin(call: ApplicationCall) {
        try {
            val request = parseAndValidateLoginRequest(call)

            adminAuthService.adminLogin(request).fold(
                onSuccess = { response ->
                    call.respond(HttpStatusCode.OK, response)
                },
                onFailure = { error ->
                    call.respondMappedAuthError(error)
                }
            )
        } catch (e: ValidationException) {
            call.respondError(
                status = HttpStatusCode.BadRequest,
                message = e.message ?: "Validation failed",
                code = AdminErrorCode.VALIDATION_ERROR
            )
        } catch (e: BadRequestException) {
            call.respondError(
                status = HttpStatusCode.BadRequest,
                message = e.message ?: "Invalid request",
                code = AdminErrorCode.INVALID_REQUEST
            )
        } catch (e: Exception) {
            call.application.log.error("Unexpected login error", e)
            call.respondError(
                status = HttpStatusCode.InternalServerError,
                message = "Authentication service error",
                code = AdminErrorCode.INTERNAL_SERVER_ERROR
            )
        }
    }

    suspend fun refreshToken(call: ApplicationCall) {
        try {
            val refreshToken = call.receive<RefreshTokenRequest>().refreshToken
            adminAuthService.refreshToken(refreshToken).fold(
                onSuccess = { response ->
                    call.respond(HttpStatusCode.OK, response)
                },
                onFailure = { error ->
                    call.respondMappedAuthError(error)
                }
            )
        } catch (e: Exception) {
            call.application.log.error("Unexpected refresh token error", e)
            call.respondError(
                status = HttpStatusCode.BadRequest,
                message = "Invalid refresh token format",
                code = AdminErrorCode.INVALID_REQUEST
            )
        }
    }

    private suspend fun parseAndValidateLoginRequest(call: ApplicationCall): AdminLoginRequest {
        val request = try {
            call.receive<AdminLoginRequest>()
        } catch (e: Exception) {
            throw BadRequestException("Invalid JSON format")
        }

        return when (val result = AdminLoginValidator.validate(request)) {
            is ValidationResult.Success -> request
            is ValidationResult.Error -> throw ValidationException(result.message)
        }
    }

    private suspend fun ApplicationCall.respondMappedAuthError(error: Throwable) {
        when (error) {
            is SecurityException -> respondError(
                status = HttpStatusCode.Unauthorized,
                message = "Invalid email or password",
                code = AdminErrorCode.INVALID_CREDENTIALS
            )
            is IllegalStateException -> respondError(
                status = HttpStatusCode.Forbidden,
                message = "Account disabled or locked",
                code = AdminErrorCode.ACCOUNT_DISABLED
            )
            is NoSuchElementException -> respondError(
                status = HttpStatusCode.Unauthorized,
                message = "Invalid credentials",
                code = AdminErrorCode.UNAUTHORIZED
            )
            else -> {
                application.log.error("Unhandled auth error", error)
                respondError(
                    status = HttpStatusCode.InternalServerError,
                    message = "Authentication service unavailable",
                    code = AdminErrorCode.INTERNAL_SERVER_ERROR
                )
            }
        }
    }
}
