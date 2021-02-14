package com.danil.kleshchin.tvseries.screens

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.screens.detailed.TvShowDetailedFragment
import com.danil.kleshchin.tvseries.screens.popular.TvShowPopularFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object CiceroneScreens {
    fun tvShowPopularScreen() = FragmentScreen { TvShowPopularFragment() }

    fun tvShowDetailedScreen(tvShowPopular: TvShowPopular) =
        FragmentScreen { TvShowDetailedFragment.newInstance(tvShowPopular) }
}
