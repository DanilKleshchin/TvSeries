package com.danil.kleshchin.tvseries.domain.interactor

import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularListUpToPageNumberUseCase
import com.danil.kleshchin.tvseries.domain.repository.popular.TvShowPopularRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTvShowPopularListUpToPageNumberUseCaseTest {

    private val pageNumber = 1
    private lateinit var getTvShowPopularListUpToPageNumberUseCase: GetTvShowPopularListUpToPageNumberUseCase

    @Mock
    private lateinit var mockTvShowRepository: TvShowPopularRepository

    @Before
    fun setUp() {
        getTvShowPopularListUpToPageNumberUseCase =
            GetTvShowPopularListUpToPageNumberUseCase(mockTvShowRepository)
    }

    @Test
    fun testGetTvShowPopularListUseCase() {
        getTvShowPopularListUpToPageNumberUseCase.execute(
            GetTvShowPopularListUpToPageNumberUseCase.Params(
                pageNumber
            )
        )

        Mockito.verify(mockTvShowRepository).getTvShowPopularListUpToPageNumberInclusive(pageNumber)
        Mockito.verifyNoMoreInteractions(mockTvShowRepository)
    }
}
