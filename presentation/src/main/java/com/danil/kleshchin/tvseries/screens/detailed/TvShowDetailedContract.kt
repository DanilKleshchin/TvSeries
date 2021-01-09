package com.danil.kleshchin.tvseries.screens.detailed

import com.danil.kleshchin.tvseries.BasePresenter
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.screens.detailed.models.TvShowDetailedModel

interface TvShowDetailedContract {

    interface View {
        fun showTvShowDetailed(tvShowDetailed: TvShowDetailedModel)
        fun showHideLoadingView(hide: Boolean)
        fun showRetry()
        fun hideRetry()
        fun showTvShowDetailedName(name: String)
    }

    interface Presenter: BasePresenter {
        fun setView(view: View)
        fun onWebPageSelected(tvShowDetailed: TvShowDetailedModel)
        fun initialize(tvShowPopular: TvShowPopular)
    }
}
