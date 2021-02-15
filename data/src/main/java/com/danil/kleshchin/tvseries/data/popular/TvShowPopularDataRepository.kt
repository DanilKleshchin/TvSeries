package com.danil.kleshchin.tvseries.data.popular

import android.content.Context
import com.danil.kleshchin.tvseries.data.popular.datasource.local.TvShowPopularLocalDataSource
import com.danil.kleshchin.tvseries.data.popular.datasource.local.TvShowPopularDataSource
import com.danil.kleshchin.tvseries.data.popular.datasource.network.TvShowPopularRemoteDataSource
import com.danil.kleshchin.tvseries.data.popular.datasource.network.utils.isNetworkAvailable
import com.danil.kleshchin.tvseries.data.popular.mapper.TvShowPopularDataMapper
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.domain.repository.popular.TvShowPopularRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class TvShowPopularDataRepository @Inject constructor(
    private val remoteDataSource: TvShowPopularRemoteDataSource,
    private val localDataSource: TvShowPopularLocalDataSource,
    private val dataStore: TvShowPopularDataStore,
    private val context: Context,
    private val mapper: TvShowPopularDataMapper,
) : TvShowPopularRepository {

    override fun getTvShowPopularListByPageNumber(pageNumber: Int): Observable<List<TvShowPopular>> {
        return Observable.concatArrayEager(
            Observable.defer {
                if (isNetworkAvailable(context)) {
                    remoteDataSource.getTvShowPopularApiResponse(pageNumber)
                        .doOnNext { list ->
                            storePagesCount(list.pages)
                            localDataSource.removeTvShowPopularByPage(pageNumber)
                                .andThen {
                                    localDataSource.insertTvShowPopularEntityList(
                                        mapper.transformApiToDbEntityList(
                                            list.tvShowListApi,
                                            pageNumber
                                        )
                                    )
                                        .subscribeOn(Schedulers.io()) //TODO needs to remove subscribes
                                        .subscribe()
                                }
                                .subscribeOn(Schedulers.io())
                                .subscribe()
                        }
                        .map(mapper::transform)
                } else {
                    localDataSource.getTvShowPopularEntityList()
                        .map(mapper::transformDbEntityList)
                }
            }
        )
    }

    override fun getTvShowPopularListUpToPageNumberInclusive(
        pageNumber: Int
    ): Observable<List<TvShowPopular>> {
        return localDataSource.getTvShowPopularEntityListUpToPageNumberInclusive(pageNumber)
            .map(mapper::transformDbEntityList)
    }

    override fun getTvShowPopularPageCount(): Observable<Int> {
        val pageCount: Int
        runBlocking {
            pageCount = dataStore.getPagesCount().first()
        }
        return Observable.just(pageCount)
    }

    private fun storePagesCount(pageCount: Int) {
        GlobalScope.launch {
            dataStore.setPagesCount(pageCount)
        }
    }
}
