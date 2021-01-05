package com.danil.kleshchin.tvseries.screens.popular

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TvShowPopularPresenter(
    private val getTvShowPopularListUseCase: GetTvShowPopularListUseCase,
    private val tvShowPopularNavigator: TvShowPopularNavigator
): TvShowPopularContract.Presenter {

    private lateinit var tvShowPopularView: TvShowPopularContract.View
    private var tvShowPopularList: List<TvShowPopular> = emptyList()

    override fun setView(view: TvShowPopularContract.View) {
        this.tvShowPopularView = view
    }

    override fun onAttach() {
        tvShowPopularView.showLoadingView()
        executeGetTvShowPopularList()
    }

    override fun onTvShowPopularSelected(tvShowPopular: TvShowPopular) {
        tvShowPopularNavigator.showDetailedScreen(tvShowPopular)
    }

    private fun executeGetTvShowPopularList() {
        getTvShowPopularListUseCase.execute(Unit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    tvShows ->
                    tvShowPopularList = tvShows
                    tvShowPopularView.showTvShowPopularList(tvShowPopularList)
                    tvShowPopularView.hideLoadingView()
                },
                {
                    it.printStackTrace()
                    tvShowPopularView.hideLoadingView()
                    tvShowPopularView.showRetry()
                }
            )
    }
}
