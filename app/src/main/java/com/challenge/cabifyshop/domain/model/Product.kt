package com.challenge.cabifyshop.domain.model

/**
 * Class that represent a Product in domain layer
 */

data class Product(
    val code: String,
    val name: String,
    val price: Double,
) {
    val productType get() = when(code) {
        "VOUCHER" -> TypeProduct.VOUCHER()
        "TSHIRT" -> TypeProduct.TSHIRT()
        "MUG" -> TypeProduct.MUG()
        else -> TypeProduct.OTHER()
    }
}