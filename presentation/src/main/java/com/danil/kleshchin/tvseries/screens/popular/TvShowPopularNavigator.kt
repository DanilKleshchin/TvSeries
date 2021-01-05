package com.danil.kleshchin.tvseries.screens.popular

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular

//TODO replace with Cicerone https://habr.com/ru/company/mobileup/blog/314838/
interface TvShowPopularNavigator {

    fun showDetailedScreen(tvShowPopular: TvShowPopular)
}
