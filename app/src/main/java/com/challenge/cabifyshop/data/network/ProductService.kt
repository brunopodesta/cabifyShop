package com.challenge.cabifyshop.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * Service class to get the products from server.
 * Use Retrofit to get access to the server
 *
 * */

class ProductService @Inject constructor(){

    private val retrofit = getRetrofit()

    suspend fun getProducts() : ApiResult {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ProductApiClient::class.java).getProducts()
            if (response.isSuccessful && !response.body()?.products.isNullOrEmpty()) {
                ApiResult.Success(response.body()?.products)
            } else {
                ApiResult.Error(response.message())
            }
        }
    }


    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}