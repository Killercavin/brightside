package com.brightside.routes.users.admin

import com.brightside.controllers.users.admin.AdminController
import com.brightside.extensions.respondError
import com.brightside.extensions.users.admin.toAdminSession
import com.brightside.security.jwt.mappers.toAdminSessionOrNull
import com.brightside.models.users.admin.dto.responses.AdminErrorCode
import com.brightside.models.users.admin.dto.responses.AdminErrorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.adminRoutes(controller: AdminController) {
    route("/api/admin") {

        // --- Public authentication routes ---
        post("/login") { controller.login(call) }
        post("/token/refresh") { controller.refreshToken(call) }

        // --- Secure routes ---
        authenticate("admin-auth") {

            // GET /api/admin/me
            get("/me") {
                val session = call.principal<JWTPrincipal>().toAdminSessionOrNull()
                if (session == null) {
                    call.respondError(
                        status = HttpStatusCode.Unauthorized,
                        message = "Invalid or missing token claims",
                        code = AdminErrorCode.UNAUTHORIZED
                    )
                    return@get
                }

                try {
                    val profile = controller.getAdminProfile(session)
                    call.respond(HttpStatusCode.OK,
                        profile)
                } catch (e: Exception) {
                    call.application.log.error("Error fetching admin profile", e)
                    call.respondError(
                        status = HttpStatusCode.InternalServerError,
                        message = "Could not retrieve admin profile",
                        code = AdminErrorCode.INTERNAL_SERVER_ERROR
                    )
                }
            }

            // GET /api/admin/{id}
            get("/{id}") {
                val principal = call.principal<JWTPrincipal>()
                if (principal == null) {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        AdminErrorResponse(
                            error = "JWT principal not found",
                            code = AdminErrorCode.UNAUTHORIZED
                        )
                    )
                    return@get
                }

                val adminSession = try {
                    principal.toAdminSession()
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        AdminErrorResponse(
                            error = "Invalid JWT claims",
                            code = AdminErrorCode.UNAUTHORIZED
                        )
                    )
                    return@get
                }

                controller.getAdminById(call, adminSession)
            }
        }
    }
}