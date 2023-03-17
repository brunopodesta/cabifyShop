package com.challenge.cabifyshop.domain

import android.content.Context
import com.challenge.cabifyshop.domain.model.Product
import com.challenge.cabifyshop.domain.model.ProductCart
import com.challenge.cabifyshop.domain.model.Promotion

/**
 * Interface to represent a Repository
 */

interface ProductRepository {
    suspend fun getProductsFromApi(): List<Product>
    suspend fun getProductsFromDatabase(): List<Product>
    suspend fun insertProductDatabase(products: List<Product>)
    suspend fun deleteProductsDatabase()
    suspend fun addProductToCart(codeProduct: String, quantity: Int)
    suspend fun getProductsFromCart(): List<ProductCart>
    suspend fun removeProductFromCart(code : String)
    fun getPromotions(context: Context): List<Promotion>
}