package com.danil.kleshchin.tvseries.domain.repository.popular

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import io.reactivex.Observable

interface TvShowPopularRepository {

    fun getTvShowPopularPageCount(): Observable<Int>

    fun getTvShowPopularListByPageNumber(pageNumber: Int): Observable<List<TvShowPopular>>

    fun getTvShowPopularListUpToPageNumberInclusive(pageNumber: Int): Observable<List<TvShowPopular>>
}
