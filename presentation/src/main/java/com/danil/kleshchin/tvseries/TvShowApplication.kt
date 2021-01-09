package com.danil.kleshchin.tvseries

import android.app.Application
import com.danil.kleshchin.tvseries.di.components.DaggerTvShowDetailedComponent
import com.danil.kleshchin.tvseries.di.components.DaggerTvShowPopularComponent
import com.danil.kleshchin.tvseries.di.components.TvShowDetailedComponent
import com.danil.kleshchin.tvseries.di.components.TvShowPopularComponent
import com.danil.kleshchin.tvseries.di.modules.AppModule
import com.danil.kleshchin.tvseries.di.modules.TvShowDetailedModule
import com.danil.kleshchin.tvseries.di.modules.TvShowPopularModule
import com.danil.kleshchin.tvseries.screens.detailed.TvShowDetailedNavigator
import com.danil.kleshchin.tvseries.screens.popular.TvShowPopularNavigator

class TvShowApplication : Application() {

    private lateinit var tvShowPopularComponent: TvShowPopularComponent
    private lateinit var tvShowDetailedComponent: TvShowDetailedComponent

    fun initTvShowPopularComponent(tvShowPopularNavigator: TvShowPopularNavigator) {
        tvShowPopularComponent = DaggerTvShowPopularComponent.builder()
            .appModule(AppModule())
            .tvShowPopularModule(TvShowPopularModule(tvShowPopularNavigator))
            .build()
    }

    fun initTvShowDetailedComponent(tvShowDetailedNavigator: TvShowDetailedNavigator) {
        tvShowDetailedComponent = DaggerTvShowDetailedComponent.builder()
            .appModule(AppModule())
            .tvShowDetailedModule(TvShowDetailedModule(tvShowDetailedNavigator))
            .build()
    }

    fun getTvShowPopularComponent() = tvShowPopularComponent

    fun getTvShowDetailedComponent() = tvShowDetailedComponent
}
/* TODO
    add network connection checking
    add empty view add retry view
    add configuration changes checking
    add caching to DB
    add custom tabs
    add default android click animation (https://guides.codepath.com/android/ripple-animation)
    add ability to sort movies by genres
    */

