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

    private val FIRST_PAGE_NUMBER = 1

    private lateinit var tvShowPopularView: TvShowPopularContract.View
    private var tvShowPopularList = ArrayList<TvShowPopular>()

    private var currentPage = FIRST_PAGE_NUMBER
    private var pageCount = currentPage

    override fun setView(view: TvShowPopularContract.View) {
        this.tvShowPopularView = view
    }

    override fun onAttach() {
        tvShowPopularView.showLoadingView()
        executeGetTvShowPopularListUseCase()
    }

    override fun onTvShowPopularSelected(tvShowPopular: TvShowPopular) {
        tvShowPopularNavigator.showDetailedScreen(tvShowPopular)
    }

    //TODO think about this. Try to combine with executeGetTvShowPopularListUseCase()
    override fun onFullTvShowListScrolled() {
        if (currentPage < pageCount) {
            currentPage++
            getTvShowPopularListUseCase.execute(
                GetTvShowPopularListUseCase.Params(currentPage)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { tvShows ->
                        tvShowPopularList.addAll(tvShows)
                        tvShowPopularView.updateTvShowPopularList(tvShowPopularList)
                        tvShowPopularView.hideLoadingView()
                    },
                    {
                        it.printStackTrace()
                        tvShowPopularView.hideLoadingView()
                        tvShowPopularView.showRetry()
                        if (currentPage > FIRST_PAGE_NUMBER) {
                            currentPage--
                        }
                    }
                )
        }
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
                    tvShowPopularList.addAll(tvShows)
                    tvShowPopularView.showTvShowPopularList(tvShowPopularList)
                    tvShowPopularView.hideLoadingView()
                },
                {
                    it.printStackTrace()
                    tvShowPopularView.hideLoadingView()
                    tvShowPopularView.showRetry()
                },
                {
                    executeGetTvShowPopularPageCountUseCase()
                }
            )
    }
}
