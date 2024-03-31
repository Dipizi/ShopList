package com.example.shoplist.domain.usecase

import androidx.lifecycle.LiveData
import com.example.shoplist.domain.entities.ShopItem
import javax.inject.Inject

class GetShopItemListUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
) {

    fun getShopItemList(): LiveData<List<ShopItem>> {
        return shopListRepository.getShopItemList()
    }
}