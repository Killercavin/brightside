package com.brightside.backend.security.jwt

import com.brightside.backend.configs.connection.EnvConfig
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.respond

fun Application.configureJwtAuth() {
    val secret = EnvConfig.getEnv("JWT_SECRET", "") ?: error("JWT_SECRET is missing")
    val issuer = EnvConfig.getEnv("JWT_ISSUER", "") ?: error("JWT_ISSUER is missing")
    val realm = EnvConfig.getEnv("JWT_REALM", "Access to admin APIs")

    val algorithm = Algorithm.HMAC256(secret)

    install(Authentication) {
        jwt("admin-auth") {
            if (realm != null) {
                this.realm = realm
            }
            verifier(
                JWT
                    .require(algorithm)
                    .withIssuer(issuer)
                    .build()
            )
            validate { credential ->
                // check if email claim exists
                val email = credential.payload.getClaim("email").asString()
                if (!email.isNullOrBlank()) JWTPrincipal(credential.payload) else null
            }
            challenge { _, _ ->
                call.respond(
                    io.ktor.http.HttpStatusCode.Unauthorized,
                    mapOf("error" to "Token is invalid or has expired")
                )
            }
        }
    }
}