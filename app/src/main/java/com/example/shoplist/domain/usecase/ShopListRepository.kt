package com.example.shoplist.domain.usecase

import androidx.lifecycle.LiveData
import com.example.shoplist.domain.entities.ShopItem

interface ShopListRepository {

    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun editShopItem(shopItem: ShopItem)
    fun getShopItemList(): LiveData<List<ShopItem>>
    suspend fun getShopItem(id: Int): ShopItem
    suspend fun removeShopItem(shopItem: ShopItem)
}