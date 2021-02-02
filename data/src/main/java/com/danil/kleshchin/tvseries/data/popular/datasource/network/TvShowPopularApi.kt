package com.danil.kleshchin.tvseries.data.popular.datasource.network

import com.danil.kleshchin.tvseries.data.popular.entity.TvShowPopularEntityObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowPopularApi {

    @GET("/api/most-popular")
    fun getTvShowPopular(@Query("page")pageNumber: Int): Observable<TvShowPopularEntityObject>
}
