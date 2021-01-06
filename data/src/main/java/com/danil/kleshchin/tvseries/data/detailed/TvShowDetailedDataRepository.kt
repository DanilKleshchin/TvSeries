package com.danil.kleshchin.tvseries.data.detailed

import com.danil.kleshchin.tvseries.data.detailed.datasource.network.TvShowDetailedRemoteDataSource
import com.danil.kleshchin.tvseries.data.detailed.mapper.TvShowDetailedDataMapper
import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed
import com.danil.kleshchin.tvseries.domain.repository.detailed.TvShowDetailedRepository
import io.reactivex.Observable
import javax.inject.Inject

class TvShowDetailedDataRepository @Inject constructor(
    private val remoteDataSource: TvShowDetailedRemoteDataSource,
    private val mapper: TvShowDetailedDataMapper
) : TvShowDetailedRepository {

    override fun getTvShowDetailed(tvShowDetailedUrl: String): Observable<TvShowDetailed> {
        return remoteDataSource.getTvShowDetailedApiResponse()
            .map(mapper::transform)
    }
}
