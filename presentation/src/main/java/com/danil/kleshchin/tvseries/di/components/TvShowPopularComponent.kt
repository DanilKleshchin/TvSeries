package com.danil.kleshchin.tvseries.di.components

import com.danil.kleshchin.tvseries.di.modules.TvShowPopularModule
import com.danil.kleshchin.tvseries.screens.popular.TvShowPopularFragment
import dagger.Component

@Component(modules = [TvShowPopularModule::class])
interface TvShowPopularComponent {

    fun inject(tvShowPopularFragment: TvShowPopularFragment)
}
