package com.example.shoplist.domain.usecase

import com.example.shoplist.domain.entities.ShopItem

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItem(id: Int): ShopItem {
       return shopListRepository.getShopItem(id)
    }
}