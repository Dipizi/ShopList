package com.example.shoplist.di.modules

import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.usecase.ShopListRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @Binds
    fun bindShopItemRepository(impl: ShopListRepositoryImpl): ShopListRepository
}