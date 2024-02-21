package com.example.shoplist.domain.usecase

import com.example.shoplist.domain.entities.ShopItem

class RemoveShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun removeShopItem(shopItem: ShopItem) {
        shopListRepository.removeShopItem(shopItem)
    }
}