package com.ecliptacare.backend.routes.requests.categories

import kotlinx.serialization.Serializable

@Serializable
data class PatchCategoryRequest(
    val name: String? = null,
    val description: String? = null
)
