package com.challenge.cabifyshop.domain.model

import com.challenge.cabifyshop.data.model.PromotionModel

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

fun PromotionModel.toDomain() = Promotion(
    code = code,
    description = description,
    products = products,
    new_price = new_price,
    min_quantity = min_quantity
)