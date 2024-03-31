package com.example.shoplist.presentation.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.domain.entities.ShopItem
import com.example.shoplist.domain.usecase.EditShopItemUseCase
import com.example.shoplist.domain.usecase.GetShopItemListUseCase
import com.example.shoplist.domain.usecase.RemoveShopItemUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopItemListUseCase: GetShopItemListUseCase,
    private val removeShopItemUseCase: RemoveShopItemUseCase,
    private val editShopItemListUseCase: EditShopItemUseCase
) : ViewModel() {


    val liveDataShopList: LiveData<List<ShopItem>> = getShopItemListUseCase.getShopItemList()

    fun editActiveStatusShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            val newItem = shopItem.copy(isEnabled = !shopItem.isEnabled)
            editShopItemListUseCase.editShopItem(newItem)
        }
    }

    fun removeShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            removeShopItemUseCase.removeShopItem(shopItem)
        }
    }
}