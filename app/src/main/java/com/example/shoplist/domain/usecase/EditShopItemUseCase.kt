package com.example.shoplist.domain.usecase

import com.example.shoplist.domain.entities.ShopItem

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}