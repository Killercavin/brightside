package com.brightside.backend.plugins.auth

import com.brightside.backend.infrastructure.redis.RedisClientProvider
import com.brightside.backend.models.users.admin.dto.responses.AdminErrorResponse
import com.brightside.backend.models.users.admin.dto.responses.ErrorCodes
import com.brightside.backend.routes.users.admin.AdminSession
import com.brightside.backend.services.users.admin.AdminService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.util.*
import kotlinx.coroutines.future.await

val AdminAuthPlugin = createRouteScopedPlugin("AdminAuthPlugin") {
    onCall { call ->
        val principal = call.principal<JWTPrincipal>()

        val email = principal?.getClaim("email", String::class)
        if (email.isNullOrBlank()) {
            call.respond(
                HttpStatusCode.Unauthorized,
                AdminErrorResponse("Invalid or missing JWT claims", ErrorCodes.UNAUTHORIZED)
            )
            return@onCall
        }

        // Redis check for active session
        val sessionKey = "refresh:$email"
        val redisSession = RedisClientProvider.asyncCommands.get(sessionKey).await()
        if (redisSession == null) {
            call.respond(
                HttpStatusCode.Unauthorized,
                AdminErrorResponse("Session expired. Please login again.", ErrorCodes.UNAUTHORIZED)
            )
            return@onCall
        }

        // Get admin details from DB
        val admin = AdminService.getAdminByEmail(email)
        if (admin == null) {
            call.respond(
                HttpStatusCode.Unauthorized,
                AdminErrorResponse("Admin not found", ErrorCodes.UNAUTHORIZED)
            )
            return@onCall
        }

        val session = AdminSession(adminId = admin.id, email = admin.email)
        call.attributes.put(AuthenticatedAdminSessionKey, session)
    }
}

val AuthenticatedAdminSessionKey = AttributeKey<AdminSession>("admin_session")
