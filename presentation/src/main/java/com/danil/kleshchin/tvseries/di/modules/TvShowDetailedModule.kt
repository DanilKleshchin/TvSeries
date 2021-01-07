package com.danil.kleshchin.tvseries.di.modules

import com.danil.kleshchin.tvseries.data.baseUrl
import com.danil.kleshchin.tvseries.data.detailed.TvShowDetailedDataRepository
import com.danil.kleshchin.tvseries.data.detailed.datasource.network.TvShowDetailedApi
import com.danil.kleshchin.tvseries.data.detailed.datasource.network.TvShowDetailedRemoteDataSource
import com.danil.kleshchin.tvseries.data.detailed.mapper.TvShowDetailedDataMapper
import com.danil.kleshchin.tvseries.domain.interactor.detailed.GetTvShowDetailedUseCase
import com.danil.kleshchin.tvseries.domain.repository.detailed.TvShowDetailedRepository
import com.danil.kleshchin.tvseries.screens.detailed.TvShowDetailedContract
import com.danil.kleshchin.tvseries.screens.detailed.TvShowDetailedNavigator
import com.danil.kleshchin.tvseries.screens.detailed.TvShowDetailedPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class TvShowDetailedModule(private val navigator: TvShowDetailedNavigator) {

    @Provides
    fun provideTvShowDetailedPresenter(
        getTvShowDetailedUseCase: GetTvShowDetailedUseCase,
        compositeDisposable: CompositeDisposable
    ): TvShowDetailedContract.Presenter =
        TvShowDetailedPresenter(getTvShowDetailedUseCase,
            navigator,
            compositeDisposable
        )

    @Provides
    fun provideTvShowRepository(
        tvShowApi: TvShowDetailedApi,
        mapper: TvShowDetailedDataMapper
    ): TvShowDetailedRepository =
        TvShowDetailedDataRepository(
            TvShowDetailedRemoteDataSource(tvShowApi),
            mapper
        )

    @Provides
    fun provideFeedApi(okHttpClient: OkHttpClient): TvShowDetailedApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvShowDetailedApi::class.java)
}
