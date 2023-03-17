package com.challenge.cabifyshop.data.model

import com.challenge.cabifyshop.domain.model.Promotion
import com.google.gson.annotations.SerializedName

/**
 * Class that represent a Promotion
 */

data class PromotionModel(@SerializedName("code") val code: String,
                          @SerializedName("description") val description: String,
                          val products : List<String> = listOf(),
                          @SerializedName("new_price") val new_price: Double,
                          @SerializedName("min_quantity") val min_quantity: Int )


fun PromotionModel.toDomain() = Promotion(
    code = code,
    description = description,
    products = products,
    new_price = new_price,
    min_quantity = min_quantity
)