package com.danil.kleshchin.tvseries.data.popular

import com.danil.kleshchin.tvseries.data.popular.datasource.local.TvShowPopularLocalDataSource
import com.danil.kleshchin.tvseries.data.popular.datasource.network.TvShowPopularRemoteDataSource
import com.danil.kleshchin.tvseries.data.popular.mapper.TvShowPopularDataMapper
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.domain.repository.popular.TvShowPopularRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TvShowPopularDataRepository @Inject constructor(
    private val remoteDataSource: TvShowPopularRemoteDataSource,
    private val localDataSource: TvShowPopularLocalDataSource,
    private val mapper: TvShowPopularDataMapper,
) : TvShowPopularRepository {

    private var pageCount: Int = 0

    override fun getTvShowPopularList(pageNumber: Int): Observable<List<TvShowPopular>> {
        return Observable.concatArrayEager(
            localDataSource.getTvShowPopularEntityList(pageNumber)
                .map(mapper::transform),
            Observable.defer {
                if (isNetworkAvailable()) {
                    remoteDataSource.getTvShowPopularApiResponse(pageNumber)
                        .doOnNext { list ->
                            pageCount = list.pages
                            localDataSource.removeAll()
                                .andThen {
                                    localDataSource.insertTvShowPopularEntityList(list.tvShowList)
                                        .subscribe()
                                }
                                .subscribeOn(Schedulers.io())
                                .subscribe()
                        }
                        .map(mapper::transform)
                } else {
                    Observable.empty()
                }
            }
        )
    }

    //TODO ask about pageCount
    override fun getTvShowPopularPageCount(): Observable<Int> {
        return Observable.just(pageCount)
    }

    //TODO
    private fun isNetworkAvailable() = true
}
