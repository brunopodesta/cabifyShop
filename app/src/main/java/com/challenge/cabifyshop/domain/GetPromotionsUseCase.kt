package com.challenge.cabifyshop.domain

import android.content.Context
import com.challenge.cabifyshop.data.ProductRepository
import com.challenge.cabifyshop.domain.model.Promotion
import com.challenge.cabifyshop.domain.model.toDomain
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Use case to get the promotions
 */

class GetPromotionsUseCase @Inject constructor(private val repository: ProductRepository,
                                               @ApplicationContext val appContext: Context) {

    operator fun invoke() : List<Promotion> {
        val response = repository.getPromotions(appContext)
        return if (response.isNotEmpty()) {
            response.map { it.toDomain() }
        } else {
            emptyList()
        }
    }

}