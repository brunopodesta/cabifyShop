package com.challenge.cabifyshop.domain

import com.challenge.cabifyshop.data.ProductRepository
import javax.inject.Inject

/**
 * Use case to remove a product from the cart
 */

class RemoveProductFromCartUseCase @Inject constructor(private val repository: ProductRepository) {

    suspend fun removeProductFromCart(code : String) {
        repository.removeProductFromCart(code)
    }
}