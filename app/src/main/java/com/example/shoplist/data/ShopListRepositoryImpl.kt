package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.entities.ShopItem
import com.example.shoplist.domain.usecase.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()

    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoGenerateId = 0

    init {
        for (i in 0 until 15) {
            val item = ShopItem(name = "Name: $i", count = i, isEnabled = Random.nextBoolean())
            addShopItem(item)
        }
    }
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNKNOWN_ID) {
            shopItem.id = autoGenerateId++
        }
        shopList.add(shopItem)
        updateLD()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        removeShopItem(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> = shopListLD

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find {
            it.id == id
        } ?: throw RuntimeException("ShopItem by id:$id not found")
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateLD()
    }

    private fun updateLD() {
        shopListLD.value = shopList.toList()
    }
}