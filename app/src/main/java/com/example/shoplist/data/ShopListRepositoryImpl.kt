package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.shoplist.data.database.ShopItemDAO
import com.example.shoplist.data.mapper.ShopItemMapper
import com.example.shoplist.domain.entities.ShopItem
import com.example.shoplist.domain.usecase.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopItemDao: ShopItemDAO,
    private val mapper: ShopItemMapper
) : ShopListRepository {


    override suspend fun addShopItem(shopItem: ShopItem) {
        val shopItemDB = mapper.mapShopItemToShopItemDb(shopItem)
        shopItemDao.addShopItem(shopItemDB)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        addShopItem(shopItem)
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> =
        shopItemDao.getShopItems().map { shopItemDb ->
            mapper.mapListShopItemDbToListShopItem(shopItemDb)
        }

    override suspend fun getShopItem(id: Int): ShopItem {
        return mapper.mapShopItemDbToShopItem(shopItemDao.getShopItem(id))

    }

    override suspend fun removeShopItem(shopItem: ShopItem) {
        shopItemDao.removeShopItem(shopItem.id)
    }
}