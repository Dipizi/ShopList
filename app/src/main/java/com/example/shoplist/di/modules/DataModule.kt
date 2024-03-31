package com.example.shoplist.di.modules

import android.app.Application
import com.example.shoplist.data.database.AppDataBase
import com.example.shoplist.data.database.ShopItemDAO
import com.example.shoplist.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideShopItemDAO(application: Application): ShopItemDAO {
        return AppDataBase.getInstance(application).getShopItemDAO()
    }
}