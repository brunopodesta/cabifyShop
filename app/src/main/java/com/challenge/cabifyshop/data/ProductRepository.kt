package com.challenge.cabifyshop.data

import android.util.Log
import com.challenge.cabifyshop.data.database.dao.ShopDao
import com.challenge.cabifyshop.data.database.entities.ProductEntity
import com.challenge.cabifyshop.data.model.ProductModel
import com.challenge.cabifyshop.data.network.ApiResult
import com.challenge.cabifyshop.data.network.ProductService
import com.challenge.cabifyshop.domain.model.Product
import com.challenge.cabifyshop.domain.model.toDomain
import com.challenge.cabifyshop.ui.viewmodel.TAG
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService,
    private val shopDao: ShopDao
) {

    suspend fun getProductsFromApi() : List<Product> {
        return when(val result = productService.getProducts()) {
            is ApiResult.Success<*> -> {
                val listProduct = result.data as  List<ProductModel>
                listProduct.map { it.toDomain() }
            }
            is ApiResult.Error -> {
                Log.d(TAG, "${result.errorMessage}")
                emptyList()
            }
        }
    }

    suspend fun getProductsFromDatabase() : List<Product> {
        val products = shopDao.getAllProducts()
        return products.map { it.toDomain() }
    }

    suspend fun insertProductDatabase(products : List<ProductEntity>) {
        shopDao.insertAllProduct(products)
    }

    suspend fun deleteProductsDatabase() {
        shopDao.deleteAll()
    }




}