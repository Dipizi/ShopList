package com.example.shoplist.presentation

import android.app.Application
import com.example.shoplist.di.components.DaggerApplicationComponent

class ShopListApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}