package com.brightside.backend.services

import com.brightside.backend.configs.DatabaseFactory.dbQuery
import com.brightside.backend.models.Category
import com.brightside.backend.models.CategoryTable
import com.brightside.backend.models.Product
import com.brightside.backend.models.ProductTable
import com.brightside.backend.services.ProductService.mapRowToProduct
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

object CategoryService {
    // get categories
    suspend fun getCategories(): List<Category> {
        return dbQuery {
            CategoryTable.selectAll().map {
                row -> Category(
                    id = row[CategoryTable.id],
                    name = row[CategoryTable.name],
                    description = row[CategoryTable.description],
                    createdAt = row[CategoryTable.createdAt],
                    updatedAt = row[CategoryTable.updatedAt]
                )
            }
        }
    }

    // Fetch products by a specific category
    suspend fun getProductsByCategory(categoryId: Int): List<Product> = dbQuery {
        (ProductTable innerJoin CategoryTable)
            .select { ProductTable.categoryId eq categoryId }
            .map { mapRowToProduct(it) }
    }
}