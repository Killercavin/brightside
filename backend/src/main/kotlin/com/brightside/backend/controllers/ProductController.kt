package com.brightside.backend.controllers

import com.brightside.backend.models.ApiResponse
import com.brightside.backend.models.Product
import com.brightside.backend.routes.requests.products.AddProductRequest
import com.brightside.backend.routes.requests.products.PatchProductRequest
import com.brightside.backend.routes.requests.products.UpdateProductRequest
import com.brightside.backend.services.ProductService
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive

object ProductController {

    // add product
    suspend fun addProduct(call: ApplicationCall): ApiResponse<Product> {
        val request = call.receive<AddProductRequest>()

        val product = ProductService.addProduct(
            name = request.name,
            description = request.description,
            price = request.price,
            categoryId = request.categoryId,
        )

        return if (product != null) {
            ApiResponse(true, "Product added successfully", product)
        } else {
            ApiResponse(false, "Failed to add product. The category may not exist.")
        }
    }

    // get products
    suspend fun getAllProducts(): List<Product> {
        return ProductService.getProducts()
    }

    // get product by id
    suspend fun getProductById(id: Int): Product? {
        return ProductService.getProductById(id)
    }

    // update product
    suspend fun updateProduct(id: Int, request: UpdateProductRequest): Product? {
        return ProductService.updateProduct(
            id = id,
            name = request.name,
            description = request.description,
            categoryId = request.categoryId,
            price = request.price
        )
    }

    // patch product
    suspend fun patchProduct(id: Int, request: PatchProductRequest): Product? {
        return ProductService.patchProduct(
            id = id,
            name = request.name,
            description = request.description,
            categoryId = request.categoryId,
            price = request.price
        )
    }

    // delete product
    suspend fun deleteProduct(id: Int): Boolean {
        return ProductService.deleteProduct(id)
    }

}