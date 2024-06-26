package com.example.shoplist.presentation.mainActivity.MainAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.shoplist.domain.entities.ShopItem
import javax.inject.Inject

class ShopItemDiffUtil @Inject constructor() : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}