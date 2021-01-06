package com.danil.kleshchin.tvseries.screens.detailed

import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed

interface TvShowDetailedContract {

    interface View {
        fun showTvShowDetailedList(tvShowDetailedList: List<TvShowDetailed>)
        fun showLoadingView()
        fun hideLoadingView()
        fun showRetry()
        fun hideRetry()
        fun showTvShowDetailedName(name: String)
    }

    interface Presenter {
        fun onAttach()
        fun setView(view: View)
        fun onDescriptionMoreSelected(tvShowDetailed: TvShowDetailed)
        fun initialize(tvShowDetailed: TvShowDetailed)
    }
}
