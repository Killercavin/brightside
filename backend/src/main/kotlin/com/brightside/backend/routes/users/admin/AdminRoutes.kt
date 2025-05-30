package com.brightside.backend.routes.users.admin

import com.brightside.backend.controllers.users.admin.AdminController
import io.ktor.server.routing.*

fun Route.adminRoutes() {
    route("/api/admin") {

        // Auth routes
        post("/login") {
            AdminController.login(call)
        }
    }
}
