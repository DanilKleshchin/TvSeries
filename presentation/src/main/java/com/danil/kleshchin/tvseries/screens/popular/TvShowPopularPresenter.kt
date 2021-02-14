package com.danil.kleshchin.tvseries.screens.popular

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularListUseCase
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularPageCountUseCase
import com.danil.kleshchin.tvseries.screens.CiceroneScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TvShowPopularPresenter(
    private val getTvShowPopularListUseCase: GetTvShowPopularListUseCase,
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
            loadTvShowPopularList{
                tvShowPopularView?.showTvShowPopularList(tvShowPopularList)
                executeGetTvShowPopularPageCountUseCase()
            }
        } else {
            tvShowPopularView?.showTvShowPopularList(tvShowPopularList)
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
            executeGetTvShowPopularListUseCase{
                tvShowPopularView?.updateTvShowPopularList(tvShowPopularList)
            }
        }
    }

    override fun onRetrySelected() {
        loadTvShowPopularList{
            tvShowPopularView?.showTvShowPopularList(tvShowPopularList)
            executeGetTvShowPopularPageCountUseCase()
        }
    }

    private fun loadTvShowPopularList(showList: () -> Unit) {
        tvShowPopularView?.showHideLoadingView(false)
        executeGetTvShowPopularListUseCase(showList)
    }

    private fun executeGetTvShowPopularListUseCase(showList: () -> Unit) {
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
                        if (tvShows.isEmpty()) {
                            tvShowPopularView?.showHideRetryView(false)
                            return@subscribe
                        }
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
