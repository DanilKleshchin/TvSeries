package com.danil.kleshchin.tvseries.screens.detailed

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.domain.interactor.detailed.GetTvShowDetailedUseCase
import com.danil.kleshchin.tvseries.screens.detailed.models.TvShowDetailedModel
import com.danil.kleshchin.tvseries.screens.detailed.models.TvShowDetailedModelMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TvShowDetailedPresenter(
    private val getTvShowDetailedUseCase: GetTvShowDetailedUseCase,
    private val tvShowDetailedNavigator: TvShowDetailedNavigator,
    private val disposables: CompositeDisposable,
    private val mapper: TvShowDetailedModelMapper
) : TvShowDetailedContract.Presenter {

    private lateinit var tvShowPopular: TvShowPopular
    private lateinit var tvShowDetailed: TvShowDetailedModel

    private var tvShowDetailedView: TvShowDetailedContract.View? = null

    override fun subscribe(view: TvShowDetailedContract.View, state: TvShowDetailedContract.State) {
        tvShowDetailedView = view
        tvShowPopular = state.getTvShowPopular()
        executeGetTvShowDetailed(tvShowPopular)
    }

    override fun unsubscribe() {
        disposables.dispose()
        tvShowDetailedView = null
    }

    //TODO ask about this state object creation
    override fun getState(): TvShowDetailedContract.State = TvShowDetailedState(tvShowPopular)

    override fun onRefreshSelected() {
        executeGetTvShowDetailed(tvShowPopular)
    }

    override fun onWebPageSelected(tvShowDetailed: TvShowDetailedModel) {
        tvShowDetailedNavigator.showWebPage(tvShowDetailed.pageUrl)
    }

    //TODO ask about disposable
    private fun executeGetTvShowDetailed(tvShowPopular: TvShowPopular) {
        tvShowDetailedView?.showHideRetryView(true)
        tvShowDetailedView?.showHideLoadingView(false)
        disposables.add(
            getTvShowDetailedUseCase.execute(
                GetTvShowDetailedUseCase.Params(tvShowPopular.detailUrl)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mapper::transform)
                .subscribe(
                    { tvShow ->
                        tvShowDetailed = tvShow
                        tvShowDetailedView?.showTvShowDetailed(tvShowDetailed)
                        tvShowDetailedView?.showHideLoadingView(true)
                        tvShowDetailedView?.showHideRetryView(true)
                    },
                    {
                        it.printStackTrace()
                        tvShowDetailedView?.showHideLoadingView(true)
                        tvShowDetailedView?.showHideRetryView(false)
                    }
                )
        )
    }
}
