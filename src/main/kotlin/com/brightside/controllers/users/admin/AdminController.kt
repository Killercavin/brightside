package com.brightside.controllers.users.admin

import com.brightside.exceptions.ForbiddenException
import com.brightside.extensions.users.admin.respondError
import com.brightside.models.users.admin.dto.requests.CreateAdminRequest
import com.brightside.models.users.admin.dto.requests.UpdateAdminRequest
import com.brightside.models.users.admin.dto.responses.AdminErrorCode
import com.brightside.models.users.admin.dto.responses.AdminProfileResponse
import com.brightside.models.users.admin.mappers.toProfileResponse
import com.brightside.routes.users.admin.AdminSession
import com.brightside.services.users.admin.AdminService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
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

        val targetAdmin = adminService.getAdminById(targetId)
        if (targetAdmin == null) {
            call.respondError(
                HttpStatusCode.NotFound,
                "Admin not found",
                AdminErrorCode.ADMIN_NOT_FOUND
            )
            return
        }

        if (!session.role.canViewAdmin(targetAdmin.role, targetId, session.adminId)) {
            call.application.log.warn("Unauthorized admin access attempt by ${session.adminId} on $targetId")
            call.respondError(
                HttpStatusCode.Forbidden,
                "You do not have permission to view this resource",
                AdminErrorCode.FORBIDDEN
            )
            return
        }

        call.respond(HttpStatusCode.OK, targetAdmin.toProfileResponse())
    }

    // getting all admins
    suspend fun getAllAdmins(call: ApplicationCall, session: AdminSession) {
        val admins = adminService.getAdminsFor(session)

        if (admins.isEmpty()) {
            call.respond(
                HttpStatusCode.OK, // or NoContent
                emptyList<AdminProfileResponse>()
            )
            return
        }

        call.respond(
            HttpStatusCode.OK,
            admins.map { it.toProfileResponse() }
        )
    }

    // creating new admin
    suspend fun createAdmin(call: ApplicationCall, session: AdminSession) {
        try {
            val request = call.receive<CreateAdminRequest>()
            val newAdmin = adminService.createAdmin(session, request)
            call.respond(HttpStatusCode.Created, newAdmin.toProfileResponse())

            call.application.log.info("Admin ${session.adminId} created new admin with email ${request.email}")
        } catch (e: BadRequestException) {
            call.respondError(HttpStatusCode.BadRequest, e.message ?: "Bad request", AdminErrorCode.INVALID_REQUEST)
        } catch (e: ForbiddenException) {
            call.respondError(HttpStatusCode.Forbidden, e.message ?: "Forbidden", AdminErrorCode.FORBIDDEN)
        } catch (e: Exception) {
            call.application.log.error("Error creating admin", e)
            call.respondError(HttpStatusCode.InternalServerError, "Unexpected error", AdminErrorCode.INTERNAL_SERVER_ERROR)
        }
    }

    // update admin
    suspend fun updateAdmin(call: ApplicationCall, session: AdminSession) {
        val targetId = call.parameters["id"]?.toIntOrNull()
            ?: return call.respondError(HttpStatusCode.BadRequest, "Missing or invalid ID", AdminErrorCode.INVALID_REQUEST)

        val targetAdmin = adminService.getAdminById(targetId)
            ?: return call.respondError(HttpStatusCode.NotFound, "Admin not found", AdminErrorCode.ADMIN_NOT_FOUND)

        if (!session.role.canEditAdmin(targetAdmin.role, targetId, session.adminId)) {
            return call.respondError(HttpStatusCode.Forbidden, "Not allowed", AdminErrorCode.FORBIDDEN)
        }

        val request = call.receive<UpdateAdminRequest>()
        val updatedAdmin = adminService.updateAdmin(
            targetId, request,
            requester = session
        )
        call.respond(HttpStatusCode.OK, updatedAdmin.toProfileResponse())
    }

}