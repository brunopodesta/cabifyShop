package com.challenge.cabifyshop.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject

/**
 * Service class to get the products from server.
 * Use Retrofit to get access to the server
 *
 * */

class ProductService @Inject constructor(private val productApiClient: ProductApiClient){

    suspend fun getProducts() : ApiResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = productApiClient.getProducts()
                if (response.isSuccessful && !response.body()?.products.isNullOrEmpty()) {
                    ApiResult.Success(response.body()?.products)
                } else {
                    ApiResult.Error(response.message())
                }
            }catch (e : HttpException) {
                ApiResult.Error(e.message())
            } catch (e : Exception) {
                ApiResult.Error(e.toString())
            }
        }
    }
}