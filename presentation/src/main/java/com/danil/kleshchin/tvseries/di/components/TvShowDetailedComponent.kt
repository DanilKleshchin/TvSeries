package com.danil.kleshchin.tvseries.di.components

import com.danil.kleshchin.tvseries.di.modules.AppModule
import com.danil.kleshchin.tvseries.di.modules.TvShowDetailedModule
import com.danil.kleshchin.tvseries.screens.detailed.TvShowDetailedFragment
import dagger.Component

@Component(modules = [AppModule::class, TvShowDetailedModule::class])
interface TvShowDetailedComponent {

    fun inject(tvShowDetailedFragment: TvShowDetailedFragment)
}
