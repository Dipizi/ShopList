package com.example.shoplist.domain.usecase

import com.example.shoplist.domain.entities.ShopItem
import javax.inject.Inject

class RemoveShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
) {

    suspend fun removeShopItem(shopItem: ShopItem) {
        shopListRepository.removeShopItem(shopItem)
    }
}