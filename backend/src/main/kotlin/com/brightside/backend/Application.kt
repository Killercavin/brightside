package com.brightside.backend

import com.brightside.backend.configs.*
import com.brightside.backend.configs.configureSecurity
import com.brightside.backend.models.CartSession
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
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
            cookie.maxAgeInSeconds = 60 * 60 * 24 * 30 // 30 days
            cookie.httpOnly = true
        }
    }

    configureSecurity()
    configureRouting()
    configureSerialization()
    configureDatabases()
}