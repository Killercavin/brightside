package com.ecliptacare.backend.routes.requests.categories

import kotlinx.serialization.Serializable

@Serializable
data class UpdateCategoryRequest(
    val name: String,
    val description: String
)
