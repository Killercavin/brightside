package com.brightside.backend.controllers.users.admin

import com.brightside.backend.extensions.users.admin.respondError
import com.brightside.backend.models.users.admin.dto.responses.AdminErrorCode
import com.brightside.backend.models.users.admin.dto.responses.AdminProfileResponse
import com.brightside.backend.models.users.admin.mappers.toProfileResponse
import com.brightside.backend.routes.users.admin.AdminSession
import com.brightside.backend.services.users.admin.AdminService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

/**
 * Main Admin Controller - orchestrates admin-related operations
 * Delegates responsibilities to dedicated sub-controllers/services
 */
class AdminController(
    private val adminService: AdminService,
    private val adminAuthController: AdminAuthController
) {

    // --- Public Authentication APIs ---
    suspend fun login(call: ApplicationCall) =
        adminAuthController.adminLogin(call)

    suspend fun refreshToken(call: ApplicationCall) =
        adminAuthController.refreshToken(call)

    // --- Authenticated Admin Operations ---
    suspend fun getAdminProfile(session: AdminSession): AdminProfileResponse =
        adminService.getAdminProfile(session.adminId)

    suspend fun getAdminById(call: ApplicationCall, session: AdminSession) {
        val targetId = call.parameters["id"]?.toIntOrNull()
        if (targetId == null) {
            call.respondError(
                HttpStatusCode.BadRequest,
                "Invalid or missing admin ID",
                AdminErrorCode.INVALID_REQUEST
            )
            return
        }

        if (!session.role.canViewAdmin(targetId, session.adminId)) {
            call.respondError(
                HttpStatusCode.Forbidden,
                "You do not have permission to view this admin",
                AdminErrorCode.FORBIDDEN
            )
            return
        }

        val admin = adminService.getAdminById(targetId)
        if (admin == null) {
            call.respondError(
                HttpStatusCode.NotFound,
                "Admin not found",
                AdminErrorCode.SERVICE_ERROR // or maybe a new AdminErrorCode.ADMIN_NOT_FOUND if needed
            )
            return
        }

        call.respond(HttpStatusCode.OK, admin.toProfileResponse())
    }
}