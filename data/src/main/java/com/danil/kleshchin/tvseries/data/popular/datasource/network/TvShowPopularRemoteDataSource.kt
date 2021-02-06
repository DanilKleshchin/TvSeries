package com.danil.kleshchin.tvseries.data.popular.datasource.network

import com.danil.kleshchin.tvseries.data.popular.datasource.network.entity.TvShowPopularApiEntityObject
import io.reactivex.Observable

class TvShowPopularRemoteDataSource(
    private val tvShowPopularApi: TvShowPopularApi
) {

    fun getTvShowPopularApiResponse(pageNumber: Int): Observable<TvShowPopularApiEntityObject> {
        return tvShowPopularApi.getTvShowPopular(pageNumber)
    }
}
