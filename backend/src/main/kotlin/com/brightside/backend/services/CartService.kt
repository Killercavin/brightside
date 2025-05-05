package com.brightside.backend.services

import com.brightside.backend.models.Cart
import com.brightside.backend.models.CartProduct
import com.brightside.backend.models.CartSession

class CartService(private val productService: ProductService) {

    suspend fun getCart(session: CartSession): Cart {
        val items = session.items.mapNotNull { cartItem ->
            val product = productService.getProductById(cartItem.productId) ?: return@mapNotNull null

            CartProduct(
                productId = product.id.value,
                quantity = cartItem.quantity,
                name = product.name,
                price = product.price.toDouble()
            )
        }

        val totalPrice = items.sumOf { it.price * it.quantity }

        return Cart(
            items = items,
            totalPrice = totalPrice
        )
    }
}
