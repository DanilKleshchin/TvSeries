package com.danil.kleshchin.tvseries.data.popular.datasource.network

import com.danil.kleshchin.tvseries.data.popular.entity.TvShowPopularEntityObject
import io.reactivex.Observable

class TvShowPopularRemoteDataSource(
    private val tvShowPopularApi: TvShowPopularApi
) {

    fun getTvShowPopularApiResponse(pageNumber: Int): Observable<TvShowPopularEntityObject> {
        return tvShowPopularApi.getTvShowPopular(pageNumber)
    }
}
