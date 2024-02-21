package com.example.shoplist.domain.usecase

import com.example.shoplist.domain.entities.ShopItem

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItemList(): List<ShopItem>
    fun getShopItem(id: Int): ShopItem
    fun removeShopItem(shopItem: ShopItem)
}