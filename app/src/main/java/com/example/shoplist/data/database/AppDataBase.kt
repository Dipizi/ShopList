package com.example.shoplist.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoplist.data.database.entities.ShopItemDB

@Database(entities = [ShopItemDB::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getShopItemDAO(): ShopItemDAO
    companion object {

        private var INSTANCE: AppDataBase? = null
        private const val NAME_DB = "appdatabase"
        private val LOCK = Any()

        fun getInstance(application: Application): AppDataBase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val dataBase = Room.databaseBuilder(
                    application,
                    AppDataBase::class.java,
                    NAME_DB
                ).build()
                INSTANCE = dataBase
                return dataBase
            }
        }
    }
}