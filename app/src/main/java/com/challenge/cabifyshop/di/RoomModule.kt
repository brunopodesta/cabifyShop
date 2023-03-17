package com.challenge.cabifyshop.di

import android.content.Context
import androidx.room.Room
import com.challenge.cabifyshop.data.database.ShopDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module to Provide ShopDatabase instance
 */

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val SHOP_DATABASE = "shop_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ShopDatabase::class.java, SHOP_DATABASE).build()

    @Singleton
    @Provides
    fun provideDao(db: ShopDatabase) = db.ShopDao()

}