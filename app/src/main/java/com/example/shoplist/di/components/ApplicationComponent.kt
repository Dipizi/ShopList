package com.example.shoplist.di.components

import android.app.Application
import com.example.shoplist.di.modules.DataModule
import com.example.shoplist.di.modules.DomainModule
import com.example.shoplist.di.scopes.ApplicationScope
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, DomainModule::class])
interface ApplicationComponent {

    fun getActivityComponent(): ActivityComponent.ActivityComponentFactory

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}