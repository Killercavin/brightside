package com.brightside.backend.routes

import com.brightside.backend.routes.users.admin.adminRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Ktor Framework")
        }

        // admin routes
        adminRoutes()
    }
}
