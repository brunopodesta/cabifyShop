package com.challenge.cabifyshop.domain

import com.challenge.cabifyshop.domain.model.ProductCart
import javax.inject.Inject

/**
 * Use case to get the list of products from the cart using the Repository class
 */

class GetProductFromCartUseCase @Inject constructor(private val repository: ProductRepository) {

    suspend operator fun invoke() : List<ProductCart> {
        val productList = repository.getProductsFromCart()
        return productList.ifEmpty {
            emptyList()
        }
    }
}