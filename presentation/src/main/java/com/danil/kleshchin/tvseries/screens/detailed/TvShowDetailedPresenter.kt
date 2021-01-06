package com.danil.kleshchin.tvseries.screens.detailed

import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed
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
        tvShowDetailedView.showLoadingView()
    }

    override fun initialize(tvShowDetailed: TvShowDetailed) {
        tvShowDetailedView.showTvShowDetailedName(tvShowDetailed.name)
        executeGetTvShowDetailed()
    }

    override fun onDescriptionMoreSelected(tvShowDetailed: TvShowDetailed) {
        tvShowDetailedNavigator.showWebPage(tvShowDetailed.moreDescriptionUrl)
    }

    private fun executeGetTvShowDetailed() {
        getTvShowDetailedUseCase.execute(
            GetTvShowDetailedUseCase.Params(tvShowDetailed.moreDescriptionUrl)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { tvShows ->
                    tvShowDetailed = tvShows
                    tvShowDetailedView.showTvShowDetailedList(listOf(tvShowDetailed))
                    tvShowDetailedView.hideLoadingView()
                },
                {
                    it.printStackTrace()
                    tvShowDetailedView.hideLoadingView()
                    tvShowDetailedView.showRetry()
                }
            )
    }
}
