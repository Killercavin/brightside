package com.brightside.backend.services

import com.brightside.backend.models.Cart
import com.brightside.backend.models.CartItem
import com.brightside.backend.models.CartProduct
import com.brightside.backend.models.CartSession

class CartService(private val productService: ProductService) {
    // get cart details
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

    // add items to the cart
    suspend fun addToCart(session: CartSession, productId: Int, quantity: Int): Boolean {
        // check if the product exists in the cart and return false if not found
        val product = productService.getProductById(productId) ?: return false

        // for existing item increase the quantity or add the product finally
        val existingItem = session.items.find {
            it.productId == productId
        }

        // Handle existing cart item to update its quantity
        if (existingItem != null) {
            existingItem.quantity += quantity // increment the item quantity
        } else {
            session.items.add(CartItem(productId, quantity)) // add the new item to the cart
        }

        return true
    }
}
