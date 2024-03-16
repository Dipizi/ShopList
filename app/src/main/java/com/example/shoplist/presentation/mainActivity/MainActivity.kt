package com.example.shoplist.presentation.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.databinding.ActivityMainBinding
import com.example.shoplist.presentation.mainActivity.MainAdapter.ShopListAdapter
import com.example.shoplist.presentation.shopItemActivity.ShopItemActivity
import com.example.shoplist.presentation.shopItemActivity.ShopItemFragment

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterShopList: ShopListAdapter
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        adapterShopList = ShopListAdapter()
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.liveDataShopList.observe(this) {
            adapterShopList.submitList(it)
        }


        adapterShopList.onShopItemLongClickListener = { viewModel.editActiveStatusShopItem(it) }
        adapterShopList.onShopItemClickListener = { itemShop ->
            if (isLandOrientation()) {
                launchFragment(ShopItemFragment.newInstanceFragmentEditMode(itemShop.id))
            } else {
                val intent = ShopItemActivity.newIntentEditMode(this, itemShop.id)
                startActivity(intent)
            }

        }

        viewBinding.floatActionButtonAddShopItem.setOnClickListener {
            if (isLandOrientation()) {
                launchFragment(ShopItemFragment.newInstanceFragmentAddMode())
            } else {
                val intent = ShopItemActivity.newIntentAddMode(this)
                startActivity(intent)
            }
        }
    }

    private fun isLandOrientation(): Boolean {
        return viewBinding.mainActivityFragmentContainerView != null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(
                R.id.mainActivityFragmentContainerView,
                fragment
            )
            .commit()
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
        itemTouchHelper.attachToRecyclerView(viewBinding.recyclerViewListShopItem)
    }

    private fun setupRecyclerView() {
        viewBinding.recyclerViewListShopItem.adapter = adapterShopList
        setupItemTouchHelperToRecyclerView()
    }

    override fun onEditingFinished() {
        supportFragmentManager.popBackStack()
    }
}