package com.brightside.backend.routes.users.admin

import com.brightside.backend.controllers.users.admin.AdminController
import com.brightside.backend.plugins.auth.AdminAuthPlugin
import com.brightside.backend.plugins.auth.AuthenticatedEmailKey
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.adminRoutes() {
    route("/api/admin") {

        // Secure sub-routes
        route("/me")  {
            install(AdminAuthPlugin)

            get {
                val email = call.attributes[AuthenticatedEmailKey]
                call.respond(HttpStatusCode.OK, mapOf("email" to email))
            }
        }

        // Auth routes
        post("/login") {
            AdminController.login(call)
        }
    }
}
