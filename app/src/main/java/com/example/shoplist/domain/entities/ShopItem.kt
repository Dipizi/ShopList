package com.example.shoplist.domain.entities

data class ShopItem(
    val id: Int,
    val name: String,
    val count: Int,
    val isEnabled: Boolean
)
