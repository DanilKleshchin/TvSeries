package com.danil.kleshchin.tvseries.screens.detailed

import com.danil.kleshchin.tvseries.BasePresenter
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.screens.detailed.models.TvShowDetailedModel

interface TvShowDetailedContract {

    interface View {
        fun showTvShowDetailed(tvShowDetailed: TvShowDetailedModel)
        fun showHideLoadingView(hide: Boolean)
        fun showHideRetryView(hide: Boolean)
    }

    interface Presenter: BasePresenter {
        fun setView(view: View)
        fun onRefreshSelected()
        fun onWebPageSelected(tvShowDetailed: TvShowDetailedModel)
        fun initialize(tvShowPopular: TvShowPopular)
    }
}
