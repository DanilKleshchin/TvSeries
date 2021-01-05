package com.danil.kleshchin.tvseries.domain.interactor.detailed

import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed
import com.danil.kleshchin.tvseries.domain.interactor.UseCase
import com.danil.kleshchin.tvseries.domain.repository.detailed.TvShowDetailedRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTvShowDetailedUseCase @Inject constructor(
        private val tvShowDetailedRepository: TvShowDetailedRepository
) : UseCase<TvShowDetailed, GetTvShowDetailedUseCase.Params> {

    override fun execute(params: Params): Observable<TvShowDetailed> {
        return tvShowDetailedRepository.getTvShowDetailed(params.tvShowDetailedUrl)
    }

    data class Params(val tvShowDetailedUrl: String)
}
