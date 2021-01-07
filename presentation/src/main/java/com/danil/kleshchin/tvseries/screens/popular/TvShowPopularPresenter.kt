package com.danil.kleshchin.tvseries.screens.popular

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularListUseCase
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularPageCountUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TvShowPopularPresenter(
    private val getTvShowPopularListUseCase: GetTvShowPopularListUseCase,
    private val getTvShowPopularPageCountUseCase: GetTvShowPopularPageCountUseCase,
    private val tvShowPopularNavigator: TvShowPopularNavigator,
    private val disposables: CompositeDisposable
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
        tvShowPopularView.showHideLoadingView(false)
        executeGetTvShowPopularListUseCase()
    }

    override fun onDetach() {
        disposables.dispose()
    }

    override fun onTvShowPopularSelected(tvShowPopular: TvShowPopular) {
        tvShowPopularNavigator.showDetailedScreen(tvShowPopular)
    }

    //TODO think about this. Try to combine with executeGetTvShowPopularListUseCase()
    override fun onFullTvShowListScrolled() {
        if (currentPage < pageCount) {
            currentPage++
            tvShowPopularView.showHideBottomLoadingView(false)
            getTvShowPopularListUseCase.execute(
                GetTvShowPopularListUseCase.Params(currentPage)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { tvShows ->
                        tvShowPopularList.addAll(tvShows)
                        tvShowPopularView.updateTvShowPopularList(tvShowPopularList)
                        tvShowPopularView.showHideBottomLoadingView(true)
                    },
                    {
                        it.printStackTrace()
                        tvShowPopularView.showHideBottomLoadingView(true)
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

    //TODO ask about disposable
    private fun executeGetTvShowPopularListUseCase() {
        disposables.add(
            getTvShowPopularListUseCase.execute(
                GetTvShowPopularListUseCase.Params(currentPage)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { tvShows ->
                        tvShowPopularList.addAll(tvShows)
                        tvShowPopularView.showTvShowPopularList(tvShowPopularList)
                        tvShowPopularView.showHideLoadingView(true)
                    },
                    {
                        it.printStackTrace()
                        tvShowPopularView.showHideLoadingView(true)
                        tvShowPopularView.showRetry()
                    },
                    {
                        executeGetTvShowPopularPageCountUseCase()
                    }
                )
        )
    }
}
