package com.example.shoplist.domain.usecase

import com.example.shoplist.domain.entities.ShopItem

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

   suspend fun addShopItem(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}