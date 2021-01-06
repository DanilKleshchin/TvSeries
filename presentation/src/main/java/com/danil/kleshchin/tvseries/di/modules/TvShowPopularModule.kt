package com.danil.kleshchin.tvseries.di.modules

import com.danil.kleshchin.tvseries.data.API_TIMEOUT_SECONDS
import com.danil.kleshchin.tvseries.data.baseUrl
import com.danil.kleshchin.tvseries.data.popular.TvShowPopularDataRepository
import com.danil.kleshchin.tvseries.data.popular.datasource.network.TvShowPopularApi
import com.danil.kleshchin.tvseries.data.popular.datasource.network.TvShowPopularRemoteDataSource
import com.danil.kleshchin.tvseries.data.popular.mapper.TvShowPopularDataMapper
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularListUseCase
import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularPageCountUseCase
import com.danil.kleshchin.tvseries.domain.repository.popular.TvShowPopularRepository
import com.danil.kleshchin.tvseries.screens.popular.TvShowPopularContract
import com.danil.kleshchin.tvseries.screens.popular.TvShowPopularNavigator
import com.danil.kleshchin.tvseries.screens.popular.TvShowPopularPresenter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class TvShowPopularModule(
    private val navigator: TvShowPopularNavigator
) {

    @Provides
    fun provideTvShowPopularPresenter(
        getTvShowPopularListUseCase: GetTvShowPopularListUseCase,
        getTvShowPopularPageCountUseCase: GetTvShowPopularPageCountUseCase
    ): TvShowPopularContract.Presenter =
        TvShowPopularPresenter(
            getTvShowPopularListUseCase,
            getTvShowPopularPageCountUseCase,
            navigator)

    @Provides
    fun provideTvShowRepository(
        tvShowApi: TvShowPopularApi,
        mapper: TvShowPopularDataMapper
    ): TvShowPopularRepository =
        TvShowPopularDataRepository(
            TvShowPopularRemoteDataSource(tvShowApi),
            mapper
        )

    @Provides
    fun provideFeedApi(okHttpClient: OkHttpClient): TvShowPopularApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvShowPopularApi::class.java)

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }
}
