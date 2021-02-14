package com.danil.kleshchin.tvseries

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.danil.kleshchin.tvseries.di.components.*
import com.danil.kleshchin.tvseries.di.modules.AppModule
import com.danil.kleshchin.tvseries.di.modules.NavigationModule
import com.danil.kleshchin.tvseries.di.modules.TvShowDetailedModule
import com.danil.kleshchin.tvseries.di.modules.TvShowPopularModule
import com.github.terrakok.cicerone.Cicerone


class TvShowApplication : Application() {

    private lateinit var tvShowPopularComponent: TvShowPopularComponent
    private lateinit var tvShowDetailedComponent: TvShowDetailedComponent
    private lateinit var navigationComponent: NavigationComponent

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    companion object {
        internal lateinit var INSTANCE: TvShowApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    fun initNavigationComponent(activity: FragmentActivity) {
        navigationComponent = DaggerNavigationComponent.builder()
            .navigationModule(NavigationModule(activity))
            .build()
    }

    fun initTvShowPopularComponent() {
        tvShowPopularComponent = DaggerTvShowPopularComponent.builder()
            .appModule(AppModule(this))
            .tvShowPopularModule(TvShowPopularModule(router))
            .build()
    }

    fun initTvShowDetailedComponent() {
        tvShowDetailedComponent = DaggerTvShowDetailedComponent.builder()
            .appModule(AppModule(this))
            .tvShowDetailedModule(TvShowDetailedModule(router))
            .build()
    }

    fun getTvShowPopularComponent() = tvShowPopularComponent

    fun getTvShowDetailedComponent() = tvShowDetailedComponent

    fun getNavigationComponent() = navigationComponent
}
