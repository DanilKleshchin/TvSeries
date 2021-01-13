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

    private var tvShowPopularView: TvShowPopularContract.View? = null
    private var tvShowPopularList = arrayListOf<TvShowPopular>()

    private var currentPageNumber = FIRST_PAGE_NUMBER
    private var pagesCount = currentPageNumber

    override fun subscribe(view: TvShowPopularContract.View, state: TvShowPopularContract.State?) {
        tvShowPopularView = view
        currentPageNumber = state?.getCurrentPageNumber() ?: FIRST_PAGE_NUMBER
        pagesCount = state?.getPagesCount() ?: currentPageNumber
        loadTvShowPopularList()
    }

    override fun unsubscribe() {
        disposables.dispose()
        tvShowPopularView = null
        tvShowPopularList = arrayListOf()
    }

    //TODO ask about this state object creation
    override fun getState(): TvShowPopularContract.State =
        TvShowPopularState(currentPageNumber, pagesCount)

    override fun onTvShowPopularSelected(tvShowPopular: TvShowPopular) {
        tvShowPopularNavigator.showDetailedScreen(tvShowPopular)
    }

    override fun onFullTvShowListScrolled() {
        if (currentPageNumber < pagesCount) {
            currentPageNumber++
            tvShowPopularView?.showHideBottomLoadingView(false)
            executeGetTvShowPopularListUseCase()
        }
    }

    override fun onRetrySelected() {
        loadTvShowPopularList()
    }

    private fun loadTvShowPopularList() {
        tvShowPopularView?.showHideLoadingView(false)
        executeGetTvShowPopularListUseCase()
    }

    //TODO ask about disposable
    //TODO try to refactor this
    private fun executeGetTvShowPopularListUseCase() {
        tvShowPopularView?.showHideRetryView(true)
        disposables.add(
            getTvShowPopularListUseCase.execute(
                GetTvShowPopularListUseCase.Params(currentPageNumber)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { tvShows ->
                        tvShowPopularView?.showHideLoadingView(true)
                        tvShowPopularView?.showHideBottomLoadingView(true)
                        tvShowPopularView?.showHideRetryView(true)
                        tvShowPopularList.addAll(tvShows)
                        if (currentPageNumber > FIRST_PAGE_NUMBER) { //FIXME this creates bug when you load two or more pages and change configuration
                            tvShowPopularView?.updateTvShowPopularList(tvShowPopularList)
                        } else {
                            tvShowPopularView?.showTvShowPopularList(tvShowPopularList)
                        }
                    },
                    {
                        it.printStackTrace()
                        tvShowPopularView?.showHideRetryView(false)
                        tvShowPopularView?.showHideLoadingView(true)
                        tvShowPopularView?.showHideBottomLoadingView(true)
                        if (currentPageNumber > FIRST_PAGE_NUMBER) {
                            currentPageNumber--
                        }
                    },
                    {
                        executeGetTvShowPopularPageCountUseCase()
                    }
                )
        )
    }

    private fun executeGetTvShowPopularPageCountUseCase() {
        getTvShowPopularPageCountUseCase.execute(Unit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                pagesCount = it
            }
    }
}
