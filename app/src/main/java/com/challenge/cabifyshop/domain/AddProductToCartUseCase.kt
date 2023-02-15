package com.challenge.cabifyshop.domain

import com.challenge.cabifyshop.data.ProductRepository
import javax.inject.Inject

/**
 * Use case to add a product into the cart using the Repository class
 */

class AddProductToCartUseCase @Inject constructor(private val repository: ProductRepository) {

    suspend fun addProductToCart(codeProduct : String, quantity : Int) {

        repository.addProductToCart(codeProduct, quantity)

    }

}