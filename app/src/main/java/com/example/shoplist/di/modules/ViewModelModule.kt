package com.example.shoplist.di.modules

import androidx.lifecycle.ViewModel
import com.example.shoplist.di.keys.ViewModelKey
import com.example.shoplist.presentation.mainActivity.MainViewModel
import com.example.shoplist.presentation.shopItemActivity.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShopItemViewModel(impl: ShopItemViewModel): ViewModel
}