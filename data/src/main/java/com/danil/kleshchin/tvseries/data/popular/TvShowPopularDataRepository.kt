package com.danil.kleshchin.tvseries.data.popular

import android.content.Context
import com.danil.kleshchin.tvseries.data.popular.datasource.local.TvShowPopularLocalDataSource
import com.danil.kleshchin.tvseries.data.popular.datasource.network.TvShowPopularRemoteDataSource
import com.danil.kleshchin.tvseries.data.popular.datasource.network.utils.isNetworkAvailable
import com.danil.kleshchin.tvseries.data.popular.mapper.TvShowPopularDataMapper
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.domain.repository.popular.TvShowPopularRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TvShowPopularDataRepository @Inject constructor(
    private val remoteDataSource: TvShowPopularRemoteDataSource,
    private val localDataSource: TvShowPopularLocalDataSource,
    private val context: Context,
    private val mapper: TvShowPopularDataMapper,
) : TvShowPopularRepository {

    private var pageCount: Int = 0

    override fun getTvShowPopularListByPageNumber(pageNumber: Int): Observable<List<TvShowPopular>> {
        return Observable.concatArrayEager(
            Observable.defer {
                if (isNetworkAvailable(context)) {
                    remoteDataSource.getTvShowPopularApiResponse(pageNumber)
                        .doOnNext { list ->
                            pageCount = list.pages
                            localDataSource.removeTvShowPopularByPage(pageNumber)
                                .andThen {
                                    localDataSource.insertTvShowPopularEntityList(
                                        mapper.transformApiToDbEntityList(
                                            list.tvShowListApi,
                                            pageNumber
                                        )
                                    )
                                        .subscribeOn(Schedulers.io())
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

    //TODO ask about pageCount
    override fun getTvShowPopularPageCount(): Observable<Int> {
        return Observable.just(pageCount)
    }
}
