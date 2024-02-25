package com.example.shoplist.presentation.MainActivity.MainAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.entities.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopListAdapter.ShopListViewHolder>(ShopItemDiffUtil()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    inner class ShopListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById<TextView>(R.id.textViewName)
        val textViewCount = itemView.findViewById<TextView>(R.id.textViewCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ShopListViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isEnabled) {
            R.layout.active_shop_item
        } else R.layout.inactive_shop_item
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.textViewName.text = shopItem.name
        holder.textViewCount.text = shopItem.count.toString()


        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }

    }
}