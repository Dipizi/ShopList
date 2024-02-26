package com.example.shoplist.presentation.ShopItemActivity

import android.content.Context
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.entities.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    lateinit var viewModel: ShopItemViewModel
    lateinit var tilName: TextInputLayout
    lateinit var tilCount: TextInputLayout
    lateinit var editTextName: EditText
    lateinit var editTextCount: EditText
    lateinit var buttonSave: Button

    private var screenMode = EXTRA_MODE_UNKNOWN
    private var shopItemId = ShopItem.UNKNOWN_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        initViews()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        setUpModeScreen()
        setTextChangedListeners()
        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.liveDataErrorName.observe(this) { isError ->
            tilName.error = if (isError) {
                getString(R.string.message_about_error_name)
            } else {
                null
            }
        }

        viewModel.liveDataErrorCount.observe(this) { isError ->
            tilCount.error = if (isError) {
                getString(R.string.message_about_error_count)
            } else {
                null
            }

        }

        viewModel.liveDataProcessFinish.observe(this) {
            finish()
        }
    }
    private fun setUpModeScreen() {
        when (screenMode) {
            EXTRA_ADD_MODE -> launchAddMode()
            EXTRA_EDIT_MODE -> launchEditMode()
        }
    }
    private fun setTextChangedListeners() {
        editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetLiveDataErrorName()
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        editTextCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetLiveDataErrorCount()
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }
    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)

        viewModel.liveDataShopItem.observe(this) { itemShop ->
            editTextName.setText(itemShop.name)
            editTextCount.setText(itemShop.count.toString())
        }

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val count = editTextCount.text.toString()
            viewModel.editShopItem(name, count)
        }
    }

    private fun launchAddMode() {

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val count = editTextCount.text.toString()
            viewModel.addShopItem(name, count)
        }
    }

    private fun initViews() {
        tilName = findViewById(R.id.textInputLayoutName)
        tilCount = findViewById(R.id.textInputLayoutCount)
        editTextName = findViewById(R.id.editTextName)
        editTextCount = findViewById(R.id.editTextCount)
        buttonSave = findViewById(R.id.buttonSave)
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