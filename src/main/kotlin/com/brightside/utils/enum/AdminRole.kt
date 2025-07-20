package com.brightside.utils.enum

import kotlinx.serialization.Serializable

@Serializable
// enum class to hold the roles
enum class AdminRole {
    SUPER_ADMIN, // can manage admins
    ADMIN,       // can manage products, orders and staff
    STAFF;       // view only, limited privileges

    fun canViewAdmin(targetRole: AdminRole, targetId: Int, requesterId: Int): Boolean {
        return when (this) {
            SUPER_ADMIN -> true
            ADMIN -> targetId == requesterId || targetRole == getDefaultRole()
            STAFF -> targetId == requesterId
        }
    }

    fun canEditAdmin(targetRole: AdminRole, targetId: Int, requesterId: Int): Boolean {
        return when (this) {
            SUPER_ADMIN -> true // Full control
            ADMIN -> (targetId == requesterId || targetRole == getDefaultRole()) // Self + staff
            STAFF -> targetId == requesterId // Only self
        }
    }

    fun canDeleteAdmin(targetId: Int, requesterId: Int): Boolean {
        return this == SUPER_ADMIN && requesterId != targetId
    }

    // Placeholder for additional permission methods, such as canAssignRole or canSuspendAdmin,
    // which may be implemented in the future to handle more specific admin actions.
}

fun getDefaultRole(): AdminRole = AdminRole.STAFF
