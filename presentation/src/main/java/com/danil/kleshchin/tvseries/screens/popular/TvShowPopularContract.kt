package com.danil.kleshchin.tvseries.screens.popular

import com.danil.kleshchin.tvseries.BasePresenter
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular

interface TvShowPopularContract {

    interface View {
        fun showTvShowPopularList(tvShowPopularList: List<TvShowPopular>)
        fun updateTvShowPopularList(tvShowPopularList: List<TvShowPopular>)
        fun showHideLoadingView(hide: Boolean)
        fun showHideBottomLoadingView(hide: Boolean)
        fun showHideRetryView(hide: Boolean)
    }

    interface Presenter: BasePresenter {
        fun setView(view: View)
        fun onRefreshSelected()
        fun onTvShowPopularSelected(tvShowPopular: TvShowPopular)
        fun onFullTvShowListScrolled()
    }
}
