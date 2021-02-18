package com.danil.kleshchin.tvseries.data.popular.datasource.network

import com.danil.kleshchin.tvseries.data.popular.datasource.network.entity.TvShowPopularApiEntityObject
import io.reactivex.Observable

interface TvShowPopularRemoteDataSource {

    fun getTvShowPopularApiResponse(pageNumber: Int): Observable<TvShowPopularApiEntityObject>
}
