package com.brightside.backend.controllers.users.admin

import io.ktor.server.application.ApplicationCall

/**
 * Main Admin Controller - delegates to specialized sub-controllers
 * Acts as a facade for all admin-related operations
 */

object AdminController {
    // Authentication operations
    suspend fun login(call: ApplicationCall) = AdminAuthController.login(call) // login
}