package com.danil.kleshchin.tvseries.di.components

import com.danil.kleshchin.tvseries.di.modules.TvShowPopularModule
import com.danil.kleshchin.tvseries.screens.popular.TvShowPopularFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [TvShowPopularModule::class])
@Singleton
interface TvShowPopularComponent {

    fun inject(tvShowPopularFragment: TvShowPopularFragment)
}
