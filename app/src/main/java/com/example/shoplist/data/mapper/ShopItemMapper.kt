package com.example.shoplist.data.mapper

import com.example.shoplist.data.database.entities.ShopItemDB
import com.example.shoplist.domain.entities.ShopItem
import javax.inject.Inject

class ShopItemMapper @Inject constructor() {

    fun mapShopItemDbToShopItem(shopItemDB: ShopItemDB) = ShopItem(
        id = shopItemDB.id,
        name = shopItemDB.name,
        count = shopItemDB.count,
        isEnabled = shopItemDB.isEnabled
    )

    fun mapShopItemToShopItemDb(shopItem: ShopItem) = ShopItemDB(
        id = shopItem.id,
        name = shopItem.name,
        count = shopItem.count,
        isEnabled = shopItem.isEnabled
    )

    fun mapListShopItemDbToListShopItem(listShopItemDb: List<ShopItemDB>) = listShopItemDb.map {
        mapShopItemDbToShopItem(it)
    }
}