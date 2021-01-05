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

    override fun getTvShowPopularList(): Observable<List<TvShowPopular>> {
        return remoteDataSource.getTvShowPopularApiResponse()
            .map(mapper::transform)
    }
}
