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
    private var tvShowPopularList = arrayListOf<TvShowPopular>()

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

    override fun onRefreshSelected() {
        executeGetTvShowPopularListUseCase()
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
        tvShowPopularView.showHideRetryView(true)
        disposables.add(
            getTvShowPopularListUseCase.execute(
                GetTvShowPopularListUseCase.Params(currentPage)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { tvShows ->
                        tvShowPopularView.showHideLoadingView(true)
                        tvShowPopularView.showHideRetryView(true)
                        tvShowPopularList = tvShows as ArrayList<TvShowPopular>
                        tvShowPopularView.showTvShowPopularList(tvShowPopularList)
                    },
                    {
                        it.printStackTrace()
                        tvShowPopularView.showHideRetryView(false)
                        tvShowPopularView.showHideLoadingView(true)
                    },
                    {
                        executeGetTvShowPopularPageCountUseCase()
                    }
                )
        )
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
                        tvShowPopularView.showHideBottomLoadingView(true)
                        tvShowPopularList.addAll(tvShows)
                        tvShowPopularView.updateTvShowPopularList(tvShowPopularList)
                    },
                    {
                        it.printStackTrace()
                        tvShowPopularView.showHideBottomLoadingView(true)
                        if (currentPage > FIRST_PAGE_NUMBER) {
                            currentPage--
                        }
                    }
                )
        }
    }
}
