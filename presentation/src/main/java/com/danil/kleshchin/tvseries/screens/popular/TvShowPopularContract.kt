package com.danil.kleshchin.tvseries.screens.popular

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular

interface TvShowPopularContract {

    interface View {
        fun showTvShowPopularList(tvShowPopularList: List<TvShowPopular>)
        fun showLoadingView()
        fun hideLoadingView()
        fun showRetry()
        fun hideRetry()
    }

    interface Presenter {
        fun onAttach()
        fun setView(view: View)
        fun onTvShowPopularSelected(tvShowPopular: TvShowPopular)
    }
}
