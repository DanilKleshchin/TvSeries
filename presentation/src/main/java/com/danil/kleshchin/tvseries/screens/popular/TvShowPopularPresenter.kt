package com.danil.kleshchin.tvseries.screens.popular

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularListByPageNumberUseCase
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularListUpToPageNumberUseCase
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularPageCountUseCase
import com.danil.kleshchin.tvseries.screens.CiceroneScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class TvShowPopularPresenter(
    private val getTvShowPopularListByPageNumberUseCase: GetTvShowPopularListByPageNumberUseCase,
    private val getTvShowPopularListUpToPageNumberUseCase: GetTvShowPopularListUpToPageNumberUseCase,
    private val getTvShowPopularPageCountUseCase: GetTvShowPopularPageCountUseCase,
    private val disposables: CompositeDisposable,
    private val router: Router
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
    }

    override fun onAttach() {
        if (tvShowPopularList.isEmpty()) {
            if (currentPageNumber > FIRST_PAGE_NUMBER) { //When the configuration was changed and current page is bigger than the first
                executeGetTvShowPopularListUseCase(
                    getTvShowPopularListUpToPageNumberUseCase.execute(
                        GetTvShowPopularListUpToPageNumberUseCase.Params(currentPageNumber)
                    )
                ) {
                    tvShowPopularView?.showTvShowPopularList(tvShowPopularList)
                    executeGetTvShowPopularPageCountUseCase()
                }
            } else {
                loadTvShowPopularList(FIRST_PAGE_NUMBER) { //Load items for the first time
                    tvShowPopularView?.showTvShowPopularList(tvShowPopularList)
                    executeGetTvShowPopularPageCountUseCase()
                }
            }
        } else {
            tvShowPopularView?.showTvShowPopularList(tvShowPopularList) //Show items when view is resumed after pausing
        }
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
        router.navigateTo(CiceroneScreens.tvShowDetailedScreen(tvShowPopular))
    }

    override fun onFullTvShowListScrolled() {
        if (currentPageNumber < pagesCount) {
            currentPageNumber++
            tvShowPopularView?.showHideBottomLoadingView(false)
            executeGetTvShowPopularListUseCase(
                getTvShowPopularListByPageNumberUseCase.execute(
                    GetTvShowPopularListByPageNumberUseCase.Params(currentPageNumber)
                )
            ) {
                tvShowPopularView?.updateTvShowPopularList(tvShowPopularList)
            }
        }
    }

    override fun onRetrySelected() {
        loadTvShowPopularList(FIRST_PAGE_NUMBER) {
            tvShowPopularView?.showTvShowPopularList(tvShowPopularList)
            executeGetTvShowPopularPageCountUseCase()
        }
    }

    private fun loadTvShowPopularList(pageNumber: Int, showList: () -> Unit) {
        tvShowPopularView?.showHideLoadingView(false)
        executeGetTvShowPopularListUseCase(
            getTvShowPopularListByPageNumberUseCase.execute(
                GetTvShowPopularListByPageNumberUseCase.Params(pageNumber)
            ), showList
        )
    }

    private fun executeGetTvShowPopularListUseCase(
        observableUseCase: Observable<List<TvShowPopular>>,
        showList: () -> Unit
    ) {
        tvShowPopularView?.showHideRetryView(true)
        disposables.add(
            observableUseCase
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { tvShows ->
                        tvShowPopularView?.showHideLoadingView(true)
                        tvShowPopularView?.showHideBottomLoadingView(true)
                        tvShowPopularView?.showHideRetryView(true)
                        if (tvShows.isEmpty()) {
                            tvShowPopularView?.showHideRetryView(false)
                            return@subscribe
                        }
                        if (tvShows.equals(tvShowPopularList)) {
                            return@subscribe //FIX this. Subscribe methods when saving to DB send the same list two times
                        }
                        tvShowPopularList.addAll(tvShows)
                        showList.invoke()
                    },
                    {
                        it.printStackTrace()
                        tvShowPopularView?.showHideRetryView(false)
                        tvShowPopularView?.showHideLoadingView(true)
                        tvShowPopularView?.showHideBottomLoadingView(true)
                        if (currentPageNumber > FIRST_PAGE_NUMBER) {
                            currentPageNumber--
                        }
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
