package com.brightside.backend.controllers

import com.brightside.backend.models.Category
import com.brightside.backend.models.Product
import com.brightside.backend.services.CategoryService
import org.jetbrains.exposed.sql.Query

object CategoryController {
    // get categories
    suspend fun getAllCategories(): List<Category> {
        return CategoryService.getCategories()
    }

    // get product by category id
    suspend fun getProductsByCategoryId(categoryId: Int): List<Product> {
        return CategoryService.getProductsByCategory(categoryId)
    }
}