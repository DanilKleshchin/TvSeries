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

    val FIRST_PAGE_NUMBER: Int = 1

    private var pageCount: Int = 0

    override fun getTvShowPopularList(pageNumber: Int): Observable<List<TvShowPopular>> {
        //No need to load data from DB when page number is greater then 1
        if (pageNumber > FIRST_PAGE_NUMBER) {
            return getTvShowPopularListByPage(pageNumber)
        }
        return getFirstTvShowPopularListPage()
    }

    //TODO ask about pageCount
    override fun getTvShowPopularPageCount(): Observable<Int> {
        return Observable.just(pageCount)
    }

    private fun getFirstTvShowPopularListPage(): Observable<List<TvShowPopular>> {
        return Observable.concatArrayEager(
            localDataSource.getTvShowPopularEntityList()
                .map(mapper::transform),
            Observable.defer {
                if (isNetworkAvailable(context)) {
                    remoteDataSource.getTvShowPopularApiResponse(FIRST_PAGE_NUMBER)
                        .doOnNext { list ->
                            pageCount = list.pages
                            localDataSource.removeAll()
                                .andThen {
                                    localDataSource.insertTvShowPopularEntityList(list.tvShowList)
                                        .subscribeOn(Schedulers.io())
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

    private fun getTvShowPopularListByPage(pageNumber: Int): Observable<List<TvShowPopular>> {
        return Observable.defer {
            if (isNetworkAvailable(context)) {
                remoteDataSource.getTvShowPopularApiResponse(pageNumber)
                    .doOnNext { list ->
                        localDataSource.insertTvShowPopularEntityList(list.tvShowList)
                            .subscribeOn(Schedulers.io())
                    }
                    .map(mapper::transform)
            } else {
                Observable.empty()
            }
        }
    }
}
