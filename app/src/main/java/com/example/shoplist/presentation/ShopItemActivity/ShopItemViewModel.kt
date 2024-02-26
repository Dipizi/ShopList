package com.example.shoplist.presentation.ShopItemActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.entities.ShopItem
import com.example.shoplist.domain.usecase.AddShopItemUseCase
import com.example.shoplist.domain.usecase.EditShopItemUseCase
import com.example.shoplist.domain.usecase.GetShopItemUseCase

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _liveDataShopItem = MutableLiveData<ShopItem>()
    val liveDataShopItem: LiveData<ShopItem> = _liveDataShopItem


    private val _liveDataErrorName = MutableLiveData<Boolean>()
    val liveDataErrorName: LiveData<Boolean>
        get() = _liveDataErrorName

    private val _liveDataErrorCount = MutableLiveData<Boolean>()
    val liveDataErrorCount: LiveData<Boolean>
        get() = _liveDataErrorCount

    private val _liveDataProcessFinish = MutableLiveData<Boolean>()
    val liveDataProcessFinish: LiveData<Boolean> = _liveDataProcessFinish
    fun getShopItem(id: Int) {
        val shopItem = getShopItemUseCase.getShopItem(id)
        _liveDataShopItem.value = shopItem
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validateFields = isValidateFields(name, count)
        if (validateFields) {
            val shopItem = ShopItem(name = name, count = count, isEnabled = true)
            addShopItemUseCase.addShopItem(shopItem)
            _liveDataProcessFinish.value = true
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validateFields = isValidateFields(name, count)
        if (validateFields) {
            _liveDataShopItem.value?.let {
                val shopItem = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(shopItem)
                _liveDataProcessFinish.value = true
            }
        }
    }

    fun resetLiveDataErrorName() {
        _liveDataErrorName.value = false
    }

    fun resetLiveDataErrorCount() {
        _liveDataErrorCount.value = false
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun isValidateFields(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _liveDataErrorName.value = true
            result = false
        }
        if (count <= 0) {
            _liveDataErrorCount.value = true
            result = false
        }
        return result
    }
}