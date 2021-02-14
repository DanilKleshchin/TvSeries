package com.danil.kleshchin.tvseries.di.modules

import android.content.Context
import com.danil.kleshchin.tvseries.data.baseUrl
import com.danil.kleshchin.tvseries.data.popular.TvShowPopularDataRepository
import com.danil.kleshchin.tvseries.data.popular.datasource.local.TvShowPopularEntityDatabase
import com.danil.kleshchin.tvseries.data.popular.datasource.local.TvShowPopularLocalDataSource
import com.danil.kleshchin.tvseries.data.popular.datasource.network.TvShowPopularApi
import com.danil.kleshchin.tvseries.data.popular.datasource.network.TvShowPopularRemoteDataSource
import com.danil.kleshchin.tvseries.data.popular.mapper.TvShowPopularDataMapper
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularListUseCase
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularPageCountUseCase
import com.danil.kleshchin.tvseries.domain.repository.popular.TvShowPopularRepository
import com.danil.kleshchin.tvseries.screens.popular.TvShowPopularContract
import com.danil.kleshchin.tvseries.screens.popular.TvShowPopularPresenter
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class TvShowPopularModule(
    private val router: Router
) {

    @Provides
    fun provideTvShowPopularPresenter(
        getTvShowPopularListUseCase: GetTvShowPopularListUseCase,
        getTvShowPopularPageCountUseCase: GetTvShowPopularPageCountUseCase,
        compositeDisposable: CompositeDisposable
    ): TvShowPopularContract.Presenter =
        TvShowPopularPresenter(
            getTvShowPopularListUseCase,
            getTvShowPopularPageCountUseCase,
            compositeDisposable,
            router
        )

    @Provides
    @Singleton
    fun provideTvShowRepository(
        tvShowApi: TvShowPopularApi,
        tvShowDatabase: TvShowPopularEntityDatabase,
        context: Context,
        mapper: TvShowPopularDataMapper
    ): TvShowPopularRepository =
        TvShowPopularDataRepository(
            TvShowPopularRemoteDataSource(tvShowApi),
            TvShowPopularLocalDataSource(tvShowDatabase),
            context,
            mapper
        )

    @Provides
    fun provideTvShowPopularDatabase(context: Context): TvShowPopularEntityDatabase =
        TvShowPopularEntityDatabase.getInstance(context)

    @Provides
    fun provideFeedApi(okHttpClient: OkHttpClient): TvShowPopularApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvShowPopularApi::class.java)
}
