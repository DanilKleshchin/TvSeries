package com.danil.kleshchin.tvseries.screens.detailed

import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.domain.interactor.detailed.GetTvShowDetailedUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TvShowDetailedPresenter(
    private val getTvShowDetailedUseCase: GetTvShowDetailedUseCase,
    private val tvShowDetailedNavigator: TvShowDetailedNavigator
) : TvShowDetailedContract.Presenter {

    private lateinit var tvShowDetailedView: TvShowDetailedContract.View
    private lateinit var tvShowDetailed: TvShowDetailed

    override fun setView(view: TvShowDetailedContract.View) {
        this.tvShowDetailedView = view
    }

    override fun onAttach() {
        tvShowDetailedView.showHideLoadingView(false)
    }

    override fun initialize(tvShowPopular: TvShowPopular) {
        tvShowDetailedView.showTvShowDetailedName(tvShowPopular.name)
        executeGetTvShowDetailed(tvShowPopular)
    }

    override fun onDescriptionMoreSelected(tvShowDetailed: TvShowDetailed) {
        tvShowDetailed.moreDescriptionUrl?.let { tvShowDetailedNavigator.showWebPage(it) }
    }

    private fun executeGetTvShowDetailed(tvShowPopular: TvShowPopular) {
        getTvShowDetailedUseCase.execute(
            GetTvShowDetailedUseCase.Params(tvShowPopular.detailUrl)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
    }
}
