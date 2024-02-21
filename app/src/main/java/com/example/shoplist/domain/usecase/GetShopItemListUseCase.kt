package com.example.shoplist.domain.usecase

import com.example.shoplist.domain.entities.ShopItem

class GetShopItemListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItemList(): List<ShopItem> {
        return shopListRepository.getShopItemList()
    }
}