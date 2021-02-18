package com.danil.kleshchin.tvseries.domain.interactor

import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularListByPageNumberUseCase
import com.danil.kleshchin.tvseries.domain.repository.popular.TvShowPopularRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTvShowPopularListByPageNumberUseCaseTest {

    private val pageNumber = 1
    private lateinit var getTvShowPopularListByPageNumberUseCase: GetTvShowPopularListByPageNumberUseCase

    @Mock
    private lateinit var mockTvShowRepository: TvShowPopularRepository

    @Before
    fun setUp() {
        getTvShowPopularListByPageNumberUseCase = GetTvShowPopularListByPageNumberUseCase(mockTvShowRepository)
    }

    @Test
    fun testGetTvShowPopularListUseCase() {
        getTvShowPopularListByPageNumberUseCase.execute(GetTvShowPopularListByPageNumberUseCase.Params(pageNumber))

        Mockito.verify(mockTvShowRepository).getTvShowPopularListByPageNumber(pageNumber)
        Mockito.verifyNoMoreInteractions(mockTvShowRepository)
    }
}
