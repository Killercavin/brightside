package com.brightside.services.users.admin

import at.favre.lib.crypto.bcrypt.BCrypt
import com.brightside.configs.connection.DatabaseFactory.dbQuery
import com.brightside.exceptions.ForbiddenException
import com.brightside.models.users.admin.dto.requests.CreateAdminRequest
import com.brightside.models.users.admin.dto.requests.UpdateAdminRequest
import com.brightside.models.users.admin.dto.responses.AdminProfileResponse
import com.brightside.models.users.admin.entities.AdminEntity
import com.brightside.models.users.admin.mappers.toAdminEntity
import com.brightside.models.users.admin.tables.AdminTable
import com.brightside.routes.users.admin.AdminSession
import com.brightside.utils.enum.AdminRole
import com.brightside.utils.enum.getDefaultRole
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.time.Instant

class AdminService {

    suspend fun getAdminProfile(id: Int): AdminProfileResponse {
        val row = dbQuery {
            AdminTable.select { AdminTable.id eq id }.singleOrNull()
        } ?: throw NotFoundException("Admin with id $id not found")

        return AdminProfileResponse(
            id = row[AdminTable.id].value,
            email = row[AdminTable.email],
            fullName = "${row[AdminTable.firstName]} ${row[AdminTable.lastName]}".trim(),
            role = row[AdminTable.role],
            createdAt = row[AdminTable.createdAt].toString(),
            updatedAt = row[AdminTable.updatedAt].toString()
        )
    }

    // fetching admin by their email
    suspend fun getAdminByEmail(email: String): AdminEntity? = dbQuery {
        AdminTable
            .select { AdminTable.email eq email }
            .singleOrNull()
            ?.let {
                AdminEntity(
                    id = it[AdminTable.id].value,
                    firstName = it[AdminTable.firstName],
                    lastName = it[AdminTable.lastName],
                    email = it[AdminTable.email],
                    passwordHash = it[AdminTable.passwordHash],
                    role = it[AdminTable.role],
                    createdAt = it[AdminTable.createdAt].toString(),
                    updatedAt = it[AdminTable.updatedAt].toString()
                )
            }
    }

    // fetch the admin by their ID
    suspend fun getAdminById(id: Int): AdminEntity? = dbQuery {
        AdminTable.select { AdminTable.id eq id }
            .singleOrNull()
            ?.toAdminEntity()
    }

    // fetch all admins
    private suspend fun getAllAdmins(): List<AdminEntity> = dbQuery {
        AdminTable.selectAll().map {
            AdminEntity(
                id = it[AdminTable.id].value,
                email = it[AdminTable.email],
                firstName = it[AdminTable.firstName],
                lastName = it[AdminTable.lastName],
                passwordHash = it[AdminTable.passwordHash],
                role = it[AdminTable.role],
                createdAt = it[AdminTable.createdAt].toString(),
                updatedAt = it[AdminTable.updatedAt].toString()
            )
        }
    }

    // check and confirm the roles
    suspend fun getAdminsFor(session: AdminSession): List<AdminEntity> {
        return when (session.role) {
            AdminRole.SUPER_ADMIN -> getAllAdmins()

            AdminRole.ADMIN -> getAllAdmins().filter { it.role == AdminRole.STAFF }

            AdminRole.STAFF -> throw ForbiddenException("You are not allowed to view this resource")
        }
    }

    // create admins => super admin role
    suspend fun createAdmin(session: AdminSession, request: CreateAdminRequest): AdminEntity {
        val allowedRolesToCreate = when (session.role) {
            AdminRole.SUPER_ADMIN -> listOf(AdminRole.SUPER_ADMIN, AdminRole.ADMIN, AdminRole.STAFF)
            AdminRole.ADMIN -> listOf(AdminRole.STAFF)
            AdminRole.STAFF -> emptyList()
        }

        if (allowedRolesToCreate.isEmpty()) {
            throw ForbiddenException("You don't have permission to create users")
        }

        val targetRole = request.role ?: getDefaultRole()

        if (targetRole !in allowedRolesToCreate) {
            throw ForbiddenException("You cannot create users with role: $targetRole")
        }

        val emailExists = dbQuery {
            AdminTable.select { AdminTable.email eq request.email }.singleOrNull()
        }

        if (emailExists != null) {
            throw BadRequestException("User with this email already exists")
        }

        val hashedPassword = BCrypt.withDefaults()
            .hashToString(12, request.password.toCharArray())

        val insertedId = dbQuery {
            AdminTable.insertAndGetId { row ->
                row[email] = request.email
                row[passwordHash] = hashedPassword
                row[firstName] = request.firstName
                row[lastName] = request.lastName
                row[role] = request.role ?: getDefaultRole()
                row[createdAt] = Instant.now()
                row[updatedAt] = Instant.now()
            }
        }

        return dbQuery {
            AdminTable
                .select { AdminTable.id eq insertedId }
                .singleOrNull()
                ?.toAdminEntity()
                ?: throw IllegalStateException("Failed to fetch created admin")
        }
    }

    // update admins
    suspend fun updateAdmin(
        targetId: Int,
        request: UpdateAdminRequest,
        requester: AdminSession
    ): AdminEntity = dbQuery {
        val row = AdminTable.select { AdminTable.id eq targetId }.singleOrNull()
            ?: throw NotFoundException("Admin not found")

        val currentRole = row[AdminTable.role]

        // Permission check
        if (!requester.role.canEditAdmin(currentRole, targetId, requester.adminId)) {
            throw ForbiddenException("You do not have permission to edit this admin")
        }

        // Role change check
        if (request.role != null && requester.role != AdminRole.SUPER_ADMIN) {
            throw ForbiddenException("Only super admins can update admin roles")
        }

        // Perform the update
        AdminTable.update({ AdminTable.id eq targetId }) {
            request.firstName?.let { firstName -> it[AdminTable.firstName] = firstName }
            request.lastName?.let { lastName -> it[AdminTable.lastName] = lastName }
            request.password?.let { password ->
                it[AdminTable.passwordHash] = BCrypt.withDefaults().hashToString(12, password.toCharArray())
            }
            request.role?.let { role -> it[AdminTable.role] = role }
            it[updatedAt] = Instant.now()
        }

        // Return the updated admin
        AdminTable.select { AdminTable.id eq targetId }.single().toAdminEntity()
    }

}