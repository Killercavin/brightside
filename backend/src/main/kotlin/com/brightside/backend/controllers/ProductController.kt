package com.brightside.backend.controllers

import com.brightside.backend.models.Product
import com.brightside.backend.routes.requests.AddProductRequest
import com.brightside.backend.services.ProductService
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column

object ProductController {

    suspend fun addProduct(request: AddProductRequest): Product? {
        return ProductService.addProduct(
            name = request.name,
            description = request.description,
            price = request.price,
            categoryId = request.categoryId,
        )
    }

    suspend fun getAllProducts(): List<Product> {
        return ProductService.getProducts()
    }

    suspend fun getProductById(id: Int): Product? {
        return ProductService.getProductById(id)
    }
}