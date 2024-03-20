package com.example.shoplist.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoplist.data.database.entities.ShopItemDB

@Dao
interface ShopItemDAO {
    @Query("SELECT * FROM shop_item")
    fun getShopItems(): LiveData<List<ShopItemDB>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItemDB: ShopItemDB)
    @Query("DELETE FROM shop_item WHERE id=:id ")
    suspend fun removeShopItem(id: Int)
    @Query("SELECT * FROM shop_item WHERE id=:id")
    suspend fun getShopItem(id: Int): ShopItemDB
}