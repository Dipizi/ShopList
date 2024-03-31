package com.example.shoplist.presentation.mainActivity.MainAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.databinding.ActiveShopItemBinding
import com.example.shoplist.databinding.InactiveShopItemBinding
import com.example.shoplist.domain.entities.ShopItem
import javax.inject.Inject

class ShopListAdapter @Inject constructor(
    shopItemDiffUtil: ShopItemDiffUtil
) : ListAdapter<ShopItem, ShopListAdapter.ShopListViewHolder>(shopItemDiffUtil) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    inner class ShopListViewHolder(val viewBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val viewBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return ShopListViewHolder(viewBinding)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isEnabled) {
            R.layout.active_shop_item
        } else R.layout.inactive_shop_item
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = getItem(position)
        val viewBinding = holder.viewBinding

        when (viewBinding) {
            is ActiveShopItemBinding -> {
                viewBinding.textViewName.text = shopItem.name
                viewBinding.textViewCount.text = shopItem.count.toString()
            }

            is InactiveShopItemBinding -> {
                viewBinding.textViewName.text = shopItem.name
                viewBinding.textViewCount.text = shopItem.count.toString()
            }
        }

        viewBinding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        viewBinding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }

    }
}