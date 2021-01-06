package com.danil.kleshchin.tvseries.data.popular.datasource.network

import com.danil.kleshchin.tvseries.data.popular.datasource.TvShowPopularDataSource
import com.danil.kleshchin.tvseries.data.popular.entity.TvShowPopularEntityObject
import io.reactivex.Observable

class TvShowPopularRemoteDataSource(
    private val tvShowPopularApi: TvShowPopularApi
) : TvShowPopularDataSource {

    override fun getTvShowPopularApiResponse(pageNumber: Int): Observable<TvShowPopularEntityObject> {
        return tvShowPopularApi.getTvShowPopular(pageNumber)
    }
}
