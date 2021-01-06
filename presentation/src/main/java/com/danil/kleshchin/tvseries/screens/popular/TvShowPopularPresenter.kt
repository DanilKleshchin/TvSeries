package com.danil.kleshchin.tvseries.screens.popular

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularListUseCase
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularPageCountUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TvShowPopularPresenter(
    private val getTvShowPopularListUseCase: GetTvShowPopularListUseCase,
    private val getTvShowPopularPageCountUseCase: GetTvShowPopularPageCountUseCase,
    private val tvShowPopularNavigator: TvShowPopularNavigator
) : TvShowPopularContract.Presenter {

    private lateinit var tvShowPopularView: TvShowPopularContract.View
    private var tvShowPopularList: List<TvShowPopular> = emptyList()

    private var currentPage = 1
    private var pageCount = currentPage

    override fun setView(view: TvShowPopularContract.View) {
        this.tvShowPopularView = view
    }

    override fun onAttach() {
        tvShowPopularView.showLoadingView()
        executeGetTvShowPopularListUseCase()
        executeGetTvShowPopularPageCountUseCase()
    }

    override fun onTvShowPopularSelected(tvShowPopular: TvShowPopular) {
        tvShowPopularNavigator.showDetailedScreen(tvShowPopular)
    }

    private fun executeGetTvShowPopularPageCountUseCase() {
        getTvShowPopularPageCountUseCase.execute(Unit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                pageCount = it
            }
    }

    private fun executeGetTvShowPopularListUseCase() {
        getTvShowPopularListUseCase.execute(
            GetTvShowPopularListUseCase.Params(currentPage)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { tvShows ->
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
