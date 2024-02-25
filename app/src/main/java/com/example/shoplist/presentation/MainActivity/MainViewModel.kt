package com.example.shoplist.presentation.MainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.entities.ShopItem
import com.example.shoplist.domain.usecase.EditShopItemUseCase
import com.example.shoplist.domain.usecase.GetShopItemListUseCase
import com.example.shoplist.domain.usecase.RemoveShopItemUseCase

class MainViewModel() : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemListUseCase = GetShopItemListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemListUseCase = EditShopItemUseCase(repository)

    val liveDataShopList: LiveData<List<ShopItem>> = getShopItemListUseCase.getShopItemList()

    fun editActiveStatusShopItem(shopItem: ShopItem) {
        val newItem = shopItem.copy(isEnabled = !shopItem.isEnabled)
        editShopItemListUseCase.editShopItem(newItem)
    }

    fun removeShopItem(shopItem: ShopItem) {
        removeShopItemUseCase.removeShopItem(shopItem)
    }
}