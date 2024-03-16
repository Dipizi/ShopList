package com.example.shoplist.presentation.mainActivity.MainAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.shoplist.domain.entities.ShopItem

class ShopItemDiffUtil: DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}