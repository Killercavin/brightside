package com.brightside.backend.plugins.auth

import com.brightside.backend.models.users.admin.dto.responses.AdminErrorResponse
import com.brightside.backend.models.users.admin.dto.responses.ErrorCodes
import com.brightside.backend.security.jwt.JwtProvider
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.createRouteScopedPlugin
import io.ktor.server.response.respond
import io.ktor.util.AttributeKey

val AdminAuthPlugin = createRouteScopedPlugin("AdminAuthPlugin") {
    onCall { call ->
        val authHeader = call.request.headers["Authorization"]

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            call.respond(
                HttpStatusCode.Unauthorized,
                AdminErrorResponse("Invalid or missing authorization header", ErrorCodes.UNAUTHORIZED)
            )
            return@onCall
        }

        val token = authHeader.removePrefix(BEARER_PREFIX).trim()

        val email: String? = try {
            JwtProvider.verifyToken(token)
        } catch (e: Exception) {
            null
        }

        if (email == null) {
            call.respond(
                HttpStatusCode.Unauthorized,
                AdminErrorResponse("Invalid or expired token", ErrorCodes.UNAUTHORIZED)
            )
            return@onCall
        }

        // store the email in call attributes
        call.attributes.put(AuthenticatedEmailKey, email)
    }
}

// key can be used to access email in your route handlers
val AuthenticatedEmailKey = AttributeKey<String>("admin_email")