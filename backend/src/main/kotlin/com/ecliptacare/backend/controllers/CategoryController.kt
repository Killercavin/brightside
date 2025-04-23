package com.ecliptacare.backend.controllers

import com.ecliptacare.backend.models.Category
import com.ecliptacare.backend.models.Product
import com.ecliptacare.backend.routes.requests.categories.AddCategoryRequest
import com.ecliptacare.backend.routes.requests.categories.PatchCategoryRequest
import com.ecliptacare.backend.routes.requests.categories.UpdateCategoryRequest
import com.ecliptacare.backend.services.CategoryService

object CategoryController {
    // get categories
    suspend fun getAllCategories(): List<Category> {
        return CategoryService.getCategories()
    }

    // get category by id
    suspend fun getCategoryById(id: Int): Category? {
        return CategoryService.getCategoryById(id)
    }

    // get product by category id
    suspend fun getProductsByCategoryId(categoryId: Int): List<Product> {
        return CategoryService.getProductsByCategory(categoryId)
    }

    // add category
    suspend fun addCategory(request: AddCategoryRequest): Category? {
        return CategoryService.addCategory(
            name = request.name,
            description = request.description
        )
    }

    // update category
    suspend fun updateCategory(id: Int, request: UpdateCategoryRequest): Category? {
        return CategoryService.updateCategory(
            id = id,
            name = request.name,
            description = request.description
        )
    }

    // patch category
    suspend fun patchCategory(id: Int, request: PatchCategoryRequest): Category? {
        return CategoryService.patchCategory(
            id = id,
            name = request.name,
            description = request.description
        )
    }

    // delete category
    suspend fun deleteCategory(id: Int): Boolean {
        return CategoryService.deleteCategory(id) > 0
    }
}