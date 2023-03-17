package com.challenge.cabifyshop.di

import com.challenge.cabifyshop.data.ProductRepositoryImpl
import com.challenge.cabifyshop.data.database.dao.ShopDao
import com.challenge.cabifyshop.data.network.ProductService
import com.challenge.cabifyshop.domain.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module to provide ProductRepositoryImpl class
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(
        productService: ProductService,
        shopDao: ShopDao
    ) = ProductRepositoryImpl(productService, shopDao) as ProductRepository

}