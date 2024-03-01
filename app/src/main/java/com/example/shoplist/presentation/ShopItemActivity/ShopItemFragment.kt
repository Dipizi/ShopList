package com.example.shoplist.presentation.ShopItemActivity

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.entities.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    lateinit var viewModel: ShopItemViewModel
    lateinit var tilName: TextInputLayout
    lateinit var tilCount: TextInputLayout
    lateinit var editTextName: EditText
    lateinit var editTextCount: EditText
    lateinit var buttonSave: Button

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var screenMode: String = EXTRA_MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNKNOWN_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)

        when (context is OnEditingFinishedListener) {
            true -> onEditingFinishedListener = context
            false -> throw RuntimeException("Activity don't implement OnEditingFinishedListener")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        setUpModeScreen()
        setTextChangedListeners()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.liveDataErrorName.observe(viewLifecycleOwner) { isError ->
            tilName.error = if (isError) {
                getString(R.string.message_about_error_name)
            } else {
                null
            }
        }

        viewModel.liveDataErrorCount.observe(viewLifecycleOwner) { isError ->
            tilCount.error = if (isError) {
                getString(R.string.message_about_error_count)
            } else {
                null
            }

        }

        viewModel.liveDataProcessFinish.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
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

        viewModel.liveDataShopItem.observe(viewLifecycleOwner) { itemShop ->
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

    private fun initViews(view: View) {
        with(view) {
            tilName = findViewById(R.id.textInputLayoutName)
            tilCount = findViewById(R.id.textInputLayoutCount)
            editTextName = findViewById(R.id.editTextName)
            editTextCount = findViewById(R.id.editTextCount)
            buttonSave = findViewById(R.id.buttonSave)
        }
    }

    private fun parseArgs() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_TYPE_MODE)) {
            throw RuntimeException("Type of arguments unknown")
        }
        val mode = args.getString(EXTRA_TYPE_MODE)
        if (mode != EXTRA_ADD_MODE && mode != EXTRA_EDIT_MODE) {
            throw RuntimeException("Mode of screen unknown: $mode")
        }
        screenMode = mode
        if (screenMode == EXTRA_EDIT_MODE) {
            if (!args.containsKey(EXTRA_ITEM_SHOP_ID)) {
                throw RuntimeException("Id of shop item unknown: $shopItemId")
            }
            shopItemId = args.getInt(EXTRA_ITEM_SHOP_ID, ShopItem.UNKNOWN_ID)
        }
    }

    interface OnEditingFinishedListener {

        fun onEditingFinished()
    }

    companion object {

        private const val EXTRA_ITEM_SHOP_ID = "EXTRA_ITEM_SHOP_ID"
        private const val EXTRA_EDIT_MODE = "EXTRA_EDIT_MODE"
        private const val EXTRA_ADD_MODE = "EXTRA_ADD_MODE"
        private const val EXTRA_TYPE_MODE = "EXTRA_TYPE_MODE"
        private const val EXTRA_MODE_UNKNOWN = "EXTRA_MODE_UNKNOWN"

        fun newInstanceFragmentEditMode(shopItemId: Int): Fragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_TYPE_MODE, EXTRA_EDIT_MODE)
                    putInt(EXTRA_ITEM_SHOP_ID, shopItemId)

                }
            }
        }

        fun newInstanceFragmentAddMode(): Fragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_TYPE_MODE, EXTRA_ADD_MODE)
                }
            }

        }
    }
}