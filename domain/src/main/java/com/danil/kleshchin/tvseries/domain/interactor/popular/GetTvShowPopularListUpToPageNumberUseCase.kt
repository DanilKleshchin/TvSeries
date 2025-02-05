package com.danil.kleshchin.tvseries.domain.interactor.popular

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.domain.interactor.UseCase
import com.danil.kleshchin.tvseries.domain.repository.popular.TvShowPopularRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTvShowPopularListUpToPageNumberUseCase @Inject constructor(
    private val tvShowPopularRepository: TvShowPopularRepository
) : UseCase<List<TvShowPopular>, GetTvShowPopularListUpToPageNumberUseCase.Params> {

    override fun execute(params: Params): Observable<List<TvShowPopular>> {
        return tvShowPopularRepository.getTvShowPopularListUpToPageNumberInclusive(params.pageNumber)
    }

    data class Params(
        val pageNumber: Int
    )
}
