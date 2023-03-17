package com.challenge.cabifyshop.domain.model

/**
 * Class that represent a Promotion in domain layer
 */

data class Promotion(
    val code: String,
    val description: String,
    val products: List<String> = listOf(),
    val new_price: Double,
    val min_quantity: Int
) {
    val promotionType get() = when(code) {
        "twoforone" -> PromotionsType.TWOFORONE()
        "bulk_purchase" -> PromotionsType.BULKPURCHASE()
        else -> PromotionsType.NONSPECIFIYC()
    }
}

