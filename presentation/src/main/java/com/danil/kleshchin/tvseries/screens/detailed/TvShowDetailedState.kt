package com.danil.kleshchin.tvseries.screens.detailed

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular

class TvShowDetailedState(
    private val tvShowPopular: TvShowPopular
) : TvShowDetailedContract.State {

    override fun getTvShowPopular(): TvShowPopular = tvShowPopular
}
