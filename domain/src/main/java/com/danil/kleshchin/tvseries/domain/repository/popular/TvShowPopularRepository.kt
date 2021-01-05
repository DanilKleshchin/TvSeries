package com.danil.kleshchin.tvseries.domain.repository.popular

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import io.reactivex.Observable

interface TvShowPopularRepository {

    fun getTvShowPopularList(): Observable<List<TvShowPopular>>
}
