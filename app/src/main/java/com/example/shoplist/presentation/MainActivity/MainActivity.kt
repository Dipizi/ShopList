package com.example.shoplist.presentation.MainActivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.presentation.MainActivity.MainAdapter.ShopListAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerViewListShopItem: RecyclerView
    private lateinit var adapterShopList: ShopListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapterShopList = ShopListAdapter()
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.liveDataShopList.observe(this) {
            adapterShopList.submitList(it)
        }


        adapterShopList.onShopItemLongClickListener = { viewModel.editActiveStatusShopItem(it) }
        adapterShopList.onShopItemClickListener = { TODO() }
    }

    private fun setupItemTouchHelperToRecyclerView() {
        val itemTouchHelperCallBack = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapterShopList.currentList[position]
                viewModel.removeShopItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerViewListShopItem)
    }

    private fun setupRecyclerView() {
        recyclerViewListShopItem = findViewById(R.id.recyclerViewListShopItem)
        recyclerViewListShopItem.adapter = adapterShopList
        setupItemTouchHelperToRecyclerView()
    }
}