package com.brightside.backend

import com.brightside.backend.configs.*
import com.brightside.backend.routes.configureRouting
import com.brightside.backend.security.configureSecurity
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
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

    configureSecurity()
    configureRouting()
    configureSerialization()
    configureDatabases()
}
