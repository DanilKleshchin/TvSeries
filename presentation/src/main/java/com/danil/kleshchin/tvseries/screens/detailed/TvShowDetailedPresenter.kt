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

    private lateinit var tvShowDetailedView: TvShowDetailedContract.View
    private lateinit var tvShowDetailed: TvShowDetailedModel

    override fun setView(view: TvShowDetailedContract.View) {
        this.tvShowDetailedView = view
    }

    override fun onAttach() {
        tvShowDetailedView.showHideLoadingView(false)
    }

    override fun onDetach() {
        disposables.dispose()
    }

    override fun initialize(tvShowPopular: TvShowPopular) {
        tvShowDetailedView.showTvShowDetailedName(tvShowPopular.name)
        executeGetTvShowDetailed(tvShowPopular)
    }

    override fun onWebPageSelected(tvShowDetailed: TvShowDetailedModel) {
        tvShowDetailedNavigator.showWebPage(tvShowDetailed.pageUrl)
    }

    //TODO ask about disposable
    private fun executeGetTvShowDetailed(tvShowPopular: TvShowPopular) {
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
                        tvShowDetailedView.showTvShowDetailed(tvShowDetailed)
                        tvShowDetailedView.showHideLoadingView(true)
                    },
                    {
                        it.printStackTrace()
                        tvShowDetailedView.showHideLoadingView(true)
                        tvShowDetailedView.showRetry()
                    }
                )
        )
    }
}
