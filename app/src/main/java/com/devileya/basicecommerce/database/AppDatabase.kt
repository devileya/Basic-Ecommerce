package com.devileya.basicecommerce.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devileya.basicecommerce.database.dao.CartDao
import com.devileya.basicecommerce.database.dao.FavoriteDao
import com.devileya.basicecommerce.database.entity.Cart
import com.devileya.basicecommerce.database.entity.Favorite

/**
 * Created by Arif Fadly Siregar 2020-03-27.
 */
@Database(entities = [Favorite::class, Cart::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun cartDao(): CartDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "ecommerceDB")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}