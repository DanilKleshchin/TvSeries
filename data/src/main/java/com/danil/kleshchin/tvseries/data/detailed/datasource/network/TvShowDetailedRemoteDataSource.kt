package com.danil.kleshchin.tvseries.data.detailed.datasource.network

import com.danil.kleshchin.tvseries.data.detailed.datasource.TvShowDetailedDataSource
import com.danil.kleshchin.tvseries.data.detailed.entity.TvShowDetailedEntityObject
import io.reactivex.Observable

class TvShowDetailedRemoteDataSource(
    private val tvShowDetailedApi: TvShowDetailedApi
) : TvShowDetailedDataSource {

    override fun getTvShowDetailedApiResponse(tvShowDetailedUrl: String): Observable<TvShowDetailedEntityObject> {
        return tvShowDetailedApi.getTvShowDetailed(tvShowDetailedUrl)
    }
}
