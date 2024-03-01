package com.example.shoplist.presentation.MainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.presentation.MainActivity.MainAdapter.ShopListAdapter
import com.example.shoplist.presentation.ShopItemActivity.ShopItemActivity
import com.example.shoplist.presentation.ShopItemActivity.ShopItemFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerViewListShopItem: RecyclerView
    private lateinit var adapterShopList: ShopListAdapter
    private lateinit var buttonAdd: FloatingActionButton
    private var fragmentContainer: FragmentContainerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentContainer = findViewById(R.id.mainActivityFragmentContainerView)
        adapterShopList = ShopListAdapter()
        setupRecyclerView()
        buttonAdd = findViewById(R.id.floatActionButtonAddShopItem)
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

        buttonAdd.setOnClickListener {
            if (isLandOrientation()) {
                launchFragment(ShopItemFragment.newInstanceFragmentAddMode())
            } else {
                val intent = ShopItemActivity.newIntentAddMode(this)
                startActivity(intent)
            }

        }
    }

    private fun isLandOrientation(): Boolean {
        return fragmentContainer != null
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
        itemTouchHelper.attachToRecyclerView(recyclerViewListShopItem)
    }

    private fun setupRecyclerView() {
        recyclerViewListShopItem = findViewById(R.id.recyclerViewListShopItem)
        recyclerViewListShopItem.adapter = adapterShopList
        setupItemTouchHelperToRecyclerView()
    }

    override fun onEditingFinished() {
        supportFragmentManager.popBackStack()
    }
}