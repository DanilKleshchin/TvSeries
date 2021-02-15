package com.danil.kleshchin.tvseries.data.popular.datasource.network

import com.danil.kleshchin.tvseries.data.popular.datasource.network.entity.TvShowPopularApiEntityObject
import io.reactivex.Observable
import javax.inject.Inject

class TvShowPopularRemoteDataSourceImpl (
    private val tvShowPopularApi: TvShowPopularApi
) : TvShowPopularRemoteDataSource {

    override fun getTvShowPopularApiResponse(
        pageNumber: Int
    ): Observable<TvShowPopularApiEntityObject> {
        return tvShowPopularApi.getTvShowPopular(pageNumber)
    }
}
