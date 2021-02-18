package com.danil.kleshchin.tvseries.di.components

import com.danil.kleshchin.tvseries.MainActivity
import com.danil.kleshchin.tvseries.di.modules.NavigationModule
import dagger.Component

@Component(modules = [NavigationModule::class])
interface NavigationComponent {
    fun inject(activity: MainActivity)
}
