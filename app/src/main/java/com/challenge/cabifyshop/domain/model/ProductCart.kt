package com.challenge.cabifyshop.domain.model


/**
 * Class that represent a Product in the cart in domain layer
 */
data class ProductCart(
    val code: String,
    val name: String,
    val price: Double,
    val quantity: Int
) {
    val productType get() = when(code) {
        "VOUCHER" -> TypeProduct.VOUCHER()
        "TSHIRT" -> TypeProduct.TSHIRT()
        "MUG" -> TypeProduct.MUG()
        else -> TypeProduct.OTHER()
    }
}