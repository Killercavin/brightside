package com.brightside.routes.users.admin

import com.brightside.controllers.users.admin.AdminController
import com.brightside.extensions.respondError
import com.brightside.extensions.users.admin.getAdminSessionOrRespond
import com.brightside.models.users.admin.dto.responses.AdminErrorCode
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.adminRoutes(controller: AdminController) {
    route("/api/v1/admin") {

        // --- Public authentication routes ---
        post("/login") { controller.login(call) }
        post("/token/refresh") { controller.refreshToken(call) }

        // --- Secure routes ---
        authenticate("admin-auth") {

            // GET /api/v1/admin/me
            get("/me") {
                val session = call.getAdminSessionOrRespond() ?: return@get

                try {
                    val profile = controller.getAdminProfile(session)
                    call.respond(HttpStatusCode.OK, profile)
                } catch (e: Exception) {
                    call.application.log.error("Error fetching admin profile", e)
                    call.respondError(
                        status = HttpStatusCode.InternalServerError,
                        message = "Could not retrieve admin profile",
                        code = AdminErrorCode.INTERNAL_SERVER_ERROR
                    )
                }
            }

            // GET /api/v1/admin/{id}
            get("/{id}") {
                val session = call.getAdminSessionOrRespond() ?: return@get
                controller.getAdminById(call, session)
            }

            // GET /api/v1/admin/admins
            get("/admins") {
                val session = call.getAdminSessionOrRespond() ?: return@get
                controller.getAllAdmins(call, session)
            }

            // POST /api/v1/admin/register
            post("/register") {
                val session = call.getAdminSessionOrRespond() ?: return@post
                controller.createAdmin(call, session)
            }

            // PUT /api/v1/admin/update
            put("/{id}") {
                val session = call.getAdminSessionOrRespond() ?: return@put
                controller.updateAdmin(call, session)
            }
        }
    }
}