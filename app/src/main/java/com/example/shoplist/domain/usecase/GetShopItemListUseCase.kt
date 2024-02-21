package com.example.shoplist.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.entities.ShopItem

class GetShopItemListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItemList(): LiveData<List<ShopItem>> {
        return shopListRepository.getShopItemList()
    }
}