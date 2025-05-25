package com.brightside.backend.configs

//import com.brightside.backend.controllers.CartController
//import com.brightside.backend.services.ProductService
//import com.brightside.backend.routes.cartRoutes
//import com.brightside.backend.routes.categoryRoutes
//import com.brightside.backend.routes.productRoutes
//import com.brightside.backend.services.CartService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
//    val productService = ProductService
//    val cartService = CartService(productService)
//    val cartController = CartController(cartService)

    routing {
        get("/") {
            call.respondText("Ktor Framework")
        }

//        // product routes
//        productRoutes()
//
//        // category routes
//        categoryRoutes()
//
//        // cart routes
//        cartRoutes(cartController)
    }
}
