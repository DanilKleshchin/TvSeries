package com.danil.kleshchin.tvseries.screens.detailed

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.screens.detailed.models.TvShowDetailedModel

interface TvShowDetailedContract {

    interface View {
        fun showTvShowDetailed(tvShowDetailed: TvShowDetailedModel)
        fun showHideLoadingView(hide: Boolean)
        fun showHideRetryView(hide: Boolean)
    }

    interface Presenter {
        fun subscribe(view: View, state: State)
        fun unsubscribe()
        fun getState(): State
        fun onRefreshSelected()
        fun onWebPageSelected(tvShowDetailed: TvShowDetailedModel)
        fun onBackPressed()
    }

    interface State {
        fun getTvShowPopular(): TvShowPopular
    }
}
