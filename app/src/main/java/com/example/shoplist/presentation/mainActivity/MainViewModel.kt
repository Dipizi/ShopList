package com.example.shoplist.presentation.mainActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.entities.ShopItem
import com.example.shoplist.domain.usecase.EditShopItemUseCase
import com.example.shoplist.domain.usecase.GetShopItemListUseCase
import com.example.shoplist.domain.usecase.RemoveShopItemUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopItemListUseCase = GetShopItemListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemListUseCase = EditShopItemUseCase(repository)

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