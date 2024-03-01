package com.example.shoplist.presentation.ShopItemActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.shoplist.R
import com.example.shoplist.domain.entities.ShopItem

class ShopItemActivity : AppCompatActivity()/*, ShopItemFragment.OnEditingFinishedListener*/ {

    private var screenMode = EXTRA_MODE_UNKNOWN
    private var shopItemId = ShopItem.UNKNOWN_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        setUpModeScreen()
    }

//    override fun onEditingFinished() {
//        finish()
//    }

    private fun setUpModeScreen() {
        when (screenMode) {
            EXTRA_ADD_MODE -> launchFragment(
                ShopItemFragment.newInstanceFragmentAddMode()
            )

            EXTRA_EDIT_MODE -> launchFragment(
                ShopItemFragment.newInstanceFragmentEditMode(shopItemId)
            )
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.shop_item_fragment_container_view,
                fragment
            )
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_TYPE_MODE)) {
            throw RuntimeException("Type of intent unknown")
        }
        val mode = intent.getStringExtra(EXTRA_TYPE_MODE)
        if (mode != EXTRA_ADD_MODE && mode != EXTRA_EDIT_MODE) {
            throw RuntimeException("Mode of screen unknown: $mode")
        }
        screenMode = mode
        if (screenMode == EXTRA_EDIT_MODE) {
            if (!intent.hasExtra(EXTRA_ITEM_SHOP_ID)) {
                throw RuntimeException("Id of shop item unknown: $shopItemId")
            }
            shopItemId = intent.getIntExtra(EXTRA_ITEM_SHOP_ID, ShopItem.UNKNOWN_ID)
        }
    }

    companion object {

        private const val EXTRA_ITEM_SHOP_ID = "EXTRA_ITEM_SHOP_ID"
        private const val EXTRA_EDIT_MODE = "EXTRA_EDIT_MODE"
        private const val EXTRA_ADD_MODE = "EXTRA_ADD_MODE"
        private const val EXTRA_TYPE_MODE = "EXTRA_TYPE_MODE"
        private const val EXTRA_MODE_UNKNOWN = "EXTRA_MODE_UNKNOWN"
        fun newIntentEditMode(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_TYPE_MODE, EXTRA_EDIT_MODE)
            intent.putExtra(EXTRA_ITEM_SHOP_ID, shopItemId)
            return intent
        }

        fun newIntentAddMode(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_TYPE_MODE, EXTRA_ADD_MODE)
            return intent
        }
    }
}