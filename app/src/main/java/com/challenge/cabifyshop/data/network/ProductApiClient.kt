package com.challenge.cabifyshop.data.network

import com.challenge.cabifyshop.data.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Define function to make requests
 *
 * */

interface ProductApiClient {

    @GET("Products.json")
    suspend fun getProducts() : Response<ProductResponse>

}