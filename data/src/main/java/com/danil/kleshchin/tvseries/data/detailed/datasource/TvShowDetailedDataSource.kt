package com.danil.kleshchin.tvseries.data.detailed.datasource

import com.danil.kleshchin.tvseries.data.detailed.entity.TvShowDetailedEntityObject
import io.reactivex.Observable

interface TvShowDetailedDataSource {

    fun getTvShowDetailedApiResponse(): Observable<TvShowDetailedEntityObject>
}
