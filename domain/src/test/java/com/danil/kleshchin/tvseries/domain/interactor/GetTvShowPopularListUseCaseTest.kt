package com.danil.kleshchin.tvseries.domain.interactor

import com.danil.kleshchin.tvseries.domain.interactor.popular.GetTvShowPopularListUseCase
import com.danil.kleshchin.tvseries.domain.repository.popular.TvShowPopularRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTvShowPopularListUseCaseTest {

    private val pageNumber = 1
    private lateinit var getTvShowPopularListUseCase: GetTvShowPopularListUseCase

    @Mock
    private lateinit var mockTvShowRepository: TvShowPopularRepository

    @Before
    fun setUp() {
        getTvShowPopularListUseCase = GetTvShowPopularListUseCase(mockTvShowRepository)
    }

    @Test
    fun testGetTvShowPopularListUseCase() {
        getTvShowPopularListUseCase.execute(GetTvShowPopularListUseCase.Params(pageNumber))

        Mockito.verify(mockTvShowRepository).getTvShowPopularList(pageNumber)
        Mockito.verifyNoMoreInteractions(mockTvShowRepository)
    }
}
