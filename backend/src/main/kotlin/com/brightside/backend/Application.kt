package com.brightside.backend

import com.brightside.backend.configs.*
import com.brightside.backend.models.CartSession
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.sessions.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    // installing content negotiation for JSON
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            serializersModule = appSerializersModule
        })
    }

    // adding session
    install(Sessions) {
        cookie<CartSession>("cart_session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 60 * 60 * 24 // 24 hours
            cookie.httpOnly = true
            cookie.extensions["SameSite"] = "Lax" // Or "None" if cross-site
            cookie.secure = true // Set to true only if using HTTPS
        }
    }

    configureSecurity()
    configureRouting()
    configureSerialization()
    configureDatabases()
}