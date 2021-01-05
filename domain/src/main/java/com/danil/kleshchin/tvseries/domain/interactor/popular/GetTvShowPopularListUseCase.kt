package com.danil.kleshchin.tvseries.domain.interactor.popular

import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.domain.interactor.UseCase
import com.danil.kleshchin.tvseries.domain.repository.popular.TvShowPopularRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTvShowPopularListUseCase @Inject constructor(
        private val tvShowPopularRepository: TvShowPopularRepository
): UseCase<List<TvShowPopular>, Unit> {

    override fun execute(params: Unit): Observable<List<TvShowPopular>> {
        return tvShowPopularRepository.getTvShowPopularList()
    }
}
