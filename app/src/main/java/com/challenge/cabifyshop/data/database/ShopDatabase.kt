package com.challenge.cabifyshop.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.challenge.cabifyshop.data.database.dao.ShopDao
import com.challenge.cabifyshop.data.database.entities.ProductEntity
import com.challenge.cabifyshop.data.database.entities.ShopCartEntity

/**
 * Shop Database
 */

@Database(entities = [ProductEntity::class, ShopCartEntity::class], version = 2)
abstract class ShopDatabase : RoomDatabase() {

    abstract fun ShopDao() : ShopDao

}