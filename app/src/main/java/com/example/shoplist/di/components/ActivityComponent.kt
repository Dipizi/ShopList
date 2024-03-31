package com.example.shoplist.di.components

import com.example.shoplist.di.modules.ViewModelModule
import com.example.shoplist.presentation.mainActivity.MainActivity
import com.example.shoplist.presentation.shopItemActivity.ShopItemFragment
import dagger.Subcomponent


@Subcomponent(modules = [ViewModelModule::class])
interface ActivityComponent {

    fun injectMainActivity(activity: MainActivity)
    fun injectShopItemFragment(fragment: ShopItemFragment)

    @Subcomponent.Factory
    interface ActivityComponentFactory {

        fun create(): ActivityComponent
    }
}