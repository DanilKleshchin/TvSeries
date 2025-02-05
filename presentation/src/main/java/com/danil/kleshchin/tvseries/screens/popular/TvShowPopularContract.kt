package com.danil.kleshchin.tvseries.screens.popular

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular

interface TvShowPopularContract {

    interface View {
        fun showTvShowPopularList(tvShowPopularList: List<TvShowPopular>)
        fun updateTvShowPopularList(tvShowPopularList: List<TvShowPopular>)
        fun showHideLoadingView(hide: Boolean)
        fun showHideBottomLoadingView(hide: Boolean)
        fun showHideRetryView(hide: Boolean)
    }

    interface Presenter {
        fun subscribe(view: View, state: State?)
        fun onAttach()
        fun unsubscribe()
        fun getState(): State
        fun onTvShowPopularSelected(tvShowPopular: TvShowPopular)
        fun onFullTvShowListScrolled()
        fun onRetrySelected()
    }

    interface State {
        fun getCurrentPageNumber(): Int
        fun getPagesCount(): Int
        fun getWasTvShowPopularListLoaded(): Boolean
    }
}
