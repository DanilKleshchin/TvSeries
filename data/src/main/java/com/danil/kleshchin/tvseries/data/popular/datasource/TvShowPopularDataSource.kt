package com.danil.kleshchin.tvseries.data.popular.datasource

import com.danil.kleshchin.tvseries.data.popular.entity.TvShowPopularEntityObject
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import io.reactivex.Observable

interface TvShowPopularDataSource {

    fun getTvShowPopularApiResponse(): Observable<TvShowPopularEntityObject>
}
