package com.brightside.backend

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            val text = "<h1>Hello from Ktor Framework API</h1>"
            val type = ContentType.parse("text/html")
            call.respondText(text, type)
        }
    }
}
