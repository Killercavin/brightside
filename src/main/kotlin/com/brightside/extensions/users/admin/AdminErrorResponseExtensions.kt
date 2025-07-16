package com.brightside.extensions.users.admin

import com.brightside.models.users.admin.dto.responses.AdminErrorCode
import com.brightside.models.users.admin.dto.responses.AdminErrorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.respondError(
    status: HttpStatusCode,
    message: String,
    code: AdminErrorCode
) {
    respond(
        status,
        AdminErrorResponse(
            error = message,
            code = code
        )
    )
}
