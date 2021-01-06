package com.danil.kleshchin.tvseries.domain.interactor.popular

import com.danil.kleshchin.tvseries.domain.interactor.UseCase
import com.danil.kleshchin.tvseries.domain.repository.popular.TvShowPopularRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTvShowPopularPageCountUseCase @Inject constructor(
    private val tvShowPopularRepository: TvShowPopularRepository
): UseCase<Int, Unit> {

    override fun execute(params: Unit): Observable<Int> {
        return tvShowPopularRepository.getTvShowPopularPageCount()
    }
}
