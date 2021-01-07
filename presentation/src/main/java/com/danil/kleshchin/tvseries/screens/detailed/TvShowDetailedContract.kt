package com.danil.kleshchin.tvseries.screens.detailed

import com.danil.kleshchin.tvseries.BasePresenter
import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular

interface TvShowDetailedContract {

    interface View {
        fun showTvShowDetailed(tvShowDetailed: TvShowDetailed)
        fun showHideLoadingView(hide: Boolean)
        fun showRetry()
        fun hideRetry()
        fun showTvShowDetailedName(name: String)
    }

    interface Presenter: BasePresenter {
        fun setView(view: View)
        fun onDescriptionMoreSelected(tvShowDetailed: TvShowDetailed)
        fun initialize(tvShowPopular: TvShowPopular)
    }
}
