package com.brightside.backend.configs

import com.brightside.backend.routes.categoryRoutes
import com.brightside.backend.routes.productRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Ktor Framework")
        }

        // product routes
        productRoutes()

        // category routes
        categoryRoutes()
    }
}
