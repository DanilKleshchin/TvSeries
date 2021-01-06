package com.danil.kleshchin.tvseries.data.detailed.datasource.network

import com.danil.kleshchin.tvseries.data.detailed.entity.TvShowDetailedEntityObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowDetailedApi {

    @GET("/api/show-details")
    fun getTvShowDetailed(@Query("q")permalink: String): Observable<TvShowDetailedEntityObject>
}
