package com.challenge.cabifyshop.di

import com.challenge.cabifyshop.data.ProductRepositoryImpl
import com.challenge.cabifyshop.domain.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun binProductRepository(repository: ProductRepositoryImpl) : ProductRepository

}