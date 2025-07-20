package com.brightside.extensions.users.admin

import com.brightside.models.users.admin.dto.responses.AdminErrorCode
import com.brightside.models.users.admin.dto.responses.AdminErrorResponse
import com.brightside.routes.users.admin.AdminSession
import com.brightside.utils.enum.AdminRole
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.jwt.*
import io.ktor.server.auth.principal
import io.ktor.server.response.respond

fun JWTPrincipal.toAdminSession(): AdminSession {
    val email = this.payload.getClaim("email").asString()
    val id = this.payload.getClaim("id").asInt()
    val role = AdminRole.valueOf(this.payload.getClaim("role").asString())

    return AdminSession(
        adminId = id,
        email = email,
        role = role
    )
}

suspend fun ApplicationCall.getAdminSessionOrRespond(): AdminSession? {
    val principal = this.principal<JWTPrincipal>()
    if (principal == null) {
        this.respond(
            HttpStatusCode.Unauthorized,
            AdminErrorResponse(
                error = "JWT principal not found",
                code = AdminErrorCode.UNAUTHORIZED
            )
        )
        return null
    }

    return try {
        principal.toAdminSession()
    } catch (e: Exception) {
        this.respond(
            HttpStatusCode.Unauthorized,
            AdminErrorResponse(
                error = "Invalid JWT claims",
                code = AdminErrorCode.UNAUTHORIZED
            )
        )
        null
    }
}
