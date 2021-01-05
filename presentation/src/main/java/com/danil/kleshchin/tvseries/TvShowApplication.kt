package com.danil.kleshchin.tvseries

import android.app.Application
import com.danil.kleshchin.tvseries.di.components.DaggerTvShowPopularComponent
import com.danil.kleshchin.tvseries.di.components.TvShowPopularComponent
import com.danil.kleshchin.tvseries.di.modules.TvShowPopularModule
import com.danil.kleshchin.tvseries.screens.popular.TvShowPopularNavigator

class TvShowApplication : Application() {

    private lateinit var tvShowPopularComponent: TvShowPopularComponent

    fun initTvShowPopularComponent(tvShowPopularNavigator: TvShowPopularNavigator) {
        tvShowPopularComponent = DaggerTvShowPopularComponent.builder()
            .tvShowPopularModule(TvShowPopularModule(tvShowPopularNavigator))
            .build()
    }

    fun getTvShowPopularComponent() = tvShowPopularComponent
}
