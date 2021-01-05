package com.danil.kleshchin.tvseries.domain.interactor

import com.danil.kleshchin.tvseries.domain.interactor.detailed.GetTvShowDetailedUseCase
import com.danil.kleshchin.tvseries.domain.repository.detailed.TvShowDetailedRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTvShowDetailedUseCaseTest {

    private val MOVIE_DETAILED_URL = "the-flash"

    private lateinit var getTvShowDetailedUseCase: GetTvShowDetailedUseCase

    @Mock
    private lateinit var mockTvShowDetailedRepository: TvShowDetailedRepository

    @Before
    fun setUp() {
        getTvShowDetailedUseCase = GetTvShowDetailedUseCase(mockTvShowDetailedRepository)
    }

    @Test
    fun testGetFeedBySectionUseCase() {
        getTvShowDetailedUseCase.execute(GetTvShowDetailedUseCase.Params(MOVIE_DETAILED_URL))

        verify(mockTvShowDetailedRepository).getTvShowDetailed(MOVIE_DETAILED_URL)
        verifyNoMoreInteractions(mockTvShowDetailedRepository)
    }
}
