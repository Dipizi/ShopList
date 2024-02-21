package com.example.shoplist.domain.entities

data class ShopItem(
    var id: Int = UNKNOWN_ID,
    val name: String,
    val count: Int,
    val isEnabled: Boolean
) {
    companion object {
        const val UNKNOWN_ID = -1
    }
}
