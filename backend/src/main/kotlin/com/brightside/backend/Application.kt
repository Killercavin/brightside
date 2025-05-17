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
    // JSON serialization with custom serializers
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            serializersModule = appSerializersModule // hook in custom serializers here
        })
    }

    // Session setup
    install(Sessions) {
        cookie<CartSession>("cart_session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 60 * 60 * 24 // 24 hours
            cookie.httpOnly = true
            cookie.extensions["SameSite"] = "Lax"
            cookie.secure = true // Enable only if using HTTPS
        }
    }

    configureSecurity()
    configureRouting()
    configureSerialization()
    configureDatabases()
}
