package com.example.shoplist.presentation.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.databinding.ActivityMainBinding
import com.example.shoplist.presentation.ShopListApp
import com.example.shoplist.presentation.mainActivity.MainAdapter.ShopListAdapter
import com.example.shoplist.presentation.shopItemActivity.ShopItemActivity
import com.example.shoplist.presentation.shopItemActivity.ShopItemFragment
import com.example.shoplist.utils.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private val activityComponent by lazy {
        (application as ShopListApp).component
            .getActivityComponent()
            .create()
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var shopListAdapter: ShopListAdapter

    private val viewBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.injectMainActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        setupRecyclerView()

        viewModel.liveDataShopList.observe(this) {
            shopListAdapter.submitList(it)
        }


        shopListAdapter.onShopItemLongClickListener = { viewModel.editActiveStatusShopItem(it) }
        shopListAdapter.onShopItemClickListener = { itemShop ->
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
                val item = shopListAdapter.currentList[position]
                viewModel.removeShopItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(viewBinding.recyclerViewListShopItem)
    }

    private fun setupRecyclerView() {
        viewBinding.recyclerViewListShopItem.adapter = shopListAdapter
        setupItemTouchHelperToRecyclerView()
    }

    override fun onEditingFinished() {
        supportFragmentManager.popBackStack()
    }
}