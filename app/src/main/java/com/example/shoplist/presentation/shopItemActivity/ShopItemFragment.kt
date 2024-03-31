package com.example.shoplist.presentation.shopItemActivity

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.databinding.FragmentShopItemBinding
import com.example.shoplist.domain.entities.ShopItem
import com.example.shoplist.presentation.ShopListApp
import com.example.shoplist.utils.ViewModelFactory
import javax.inject.Inject

class ShopItemFragment : Fragment() {

    private val activityComponent by lazy {
        (requireActivity().application as ShopListApp).component
            .getActivityComponent()
            .create()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ShopItemViewModel::class.java]
    }

    private var _viewBinding: FragmentShopItemBinding? = null
    private val viewBinding: FragmentShopItemBinding
        get() = _viewBinding ?: throw RuntimeException("binding is null")

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var screenMode: String = EXTRA_MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNKNOWN_ID

    override fun onAttach(context: Context) {
        activityComponent.injectShopItemFragment(this)
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
    ): View {
        _viewBinding = FragmentShopItemBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = viewLifecycleOwner
        setUpModeScreen()
        setTextChangedListeners()
        observeLiveData()
    }

    private fun observeLiveData() {
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
        viewBinding.editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetLiveDataErrorName()
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        viewBinding.editTextCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetLiveDataErrorCount()
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)

        viewBinding.buttonSave.setOnClickListener {
            val name = viewBinding.editTextName.text.toString()
            val count = viewBinding.editTextCount.text.toString()
            viewModel.editShopItem(name, count)
        }
    }

    private fun launchAddMode() {

        viewBinding.buttonSave.setOnClickListener {
            val name = viewBinding.editTextName.text.toString()
            val count = viewBinding.editTextCount.text.toString()
            viewModel.addShopItem(name, count)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
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