package com.brightside.backend.routes.users.admin

import com.brightside.backend.controllers.users.admin.AdminController
import com.brightside.backend.models.users.admin.dto.responses.AdminErrorResponse
import com.brightside.backend.models.users.admin.dto.responses.ErrorCodes
import com.brightside.backend.services.users.admin.AdminService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.adminRoutes() {
    route("/api/admin") {

        // Secure sub-routes
        authenticate("admin-auth") {
            get("/me") {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()

                if (email.isNullOrBlank()) {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        AdminErrorResponse("Invalid or missing JWT claims", ErrorCodes.UNAUTHORIZED)
                    )
                    return@get
                }

                val admin = AdminService.getAdminByEmail(email)
                if (admin == null) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        AdminErrorResponse("Admin not found", ErrorCodes.UNAUTHORIZED)
                    )
                    return@get
                }

                val profile = AdminController.getAdminProfile(
                    AdminSession(email = email, adminId = admin.id)
                )

                call.respond(HttpStatusCode.OK, profile)
            }
        }


        // Auth routes
        post("/login") {
            AdminController.login(call)
        }
    }
}
