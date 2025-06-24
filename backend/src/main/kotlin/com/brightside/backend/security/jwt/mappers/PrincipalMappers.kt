package com.brightside.backend.security.jwt.mappers

import com.brightside.backend.routes.users.admin.AdminSession
import com.brightside.backend.utils.emum.AdminRole
import io.ktor.server.auth.jwt.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("JWTPrincipalMapper")

fun JWTPrincipal?.toAdminSessionOrNull(): AdminSession? {
    if (this == null) {
        logger.warn("JWTPrincipal is null")
        return null
    }

    val payload = this.payload

    val id = runCatching { payload.getClaim("id").asInt() }
        .onFailure { logger.warn("Missing or invalid claim: id") }
        .getOrNull()

    val email = runCatching { payload.getClaim("email").asString() }
        .onFailure { logger.warn("Missing or invalid claim: email") }
        .getOrNull()

    val role = runCatching {
        val roleStr = payload.getClaim("role").asString()
        AdminRole.valueOf(roleStr.uppercase())
    }.onFailure {
        logger.warn("Invalid or missing role claim: ${it.message}")
    }.getOrNull()

    if (id == null || email == null || role == null) {
        logger.warn("Failed to construct AdminSession due to missing or invalid claims")
        return null
    }

    return AdminSession(
        adminId = id,
        email = email,
        role = role
    )
}
