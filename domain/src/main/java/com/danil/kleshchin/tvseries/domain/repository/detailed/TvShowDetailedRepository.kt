package com.danil.kleshchin.tvseries.domain.repository.detailed

import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed
import io.reactivex.Observable

interface TvShowDetailedRepository {

    fun getTvShowDetailed(tvShowDetailedUrl: String): Observable<TvShowDetailed>
}
