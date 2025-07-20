package com.brightside.plugins

import com.brightside.exceptions.ForbiddenException
import com.brightside.models.users.admin.dto.responses.AdminErrorCode
import com.brightside.models.users.admin.dto.responses.AdminErrorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<ForbiddenException> { call, cause ->
            call.respond(
                HttpStatusCode.Forbidden,
                AdminErrorResponse(
                    error = cause.message ?: "Access denied",
                    code = AdminErrorCode.FORBIDDEN
                )
            )
        }

        // Optional: Handle all other unhandled exceptions
        exception<Throwable> { call, cause ->
            call.application.environment.log.error("Unhandled exception", cause)
            call.respond(
                HttpStatusCode.InternalServerError,
                AdminErrorResponse(
                    error = "Internal server error",
                    code = AdminErrorCode.INTERNAL_ERROR
                )
            )
        }
    }
}