package com.danil.kleshchin.tvseries.data.popular

import com.danil.kleshchin.tvseries.data.popular.datasource.network.TvShowPopularRemoteDataSource
import com.danil.kleshchin.tvseries.data.popular.mapper.TvShowPopularDataMapper
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.domain.repository.popular.TvShowPopularRepository
import io.reactivex.Observable
import javax.inject.Inject

class TvShowPopularDataRepository @Inject constructor(
    private val remoteDataSource: TvShowPopularRemoteDataSource,
    private val mapper: TvShowPopularDataMapper
) : TvShowPopularRepository {

    private var pageCount: Int = 0

    override fun getTvShowPopularList(pageNumber: Int): Observable<List<TvShowPopular>> {
        return remoteDataSource.getTvShowPopularApiResponse(pageNumber)
            .doOnNext{
                pageCount = it.pages
            }
            .map(mapper::transform)
    }

    override fun getTvShowPopularPageCount(): Observable<Int> {
        return Observable.just(pageCount)
    }
}
