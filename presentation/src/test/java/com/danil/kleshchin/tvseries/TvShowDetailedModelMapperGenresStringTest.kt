package com.danil.kleshchin.tvseries

import com.danil.kleshchin.tvseries.screens.detailed.models.TvShowDetailedModelMapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TvShowDetailedModelMapperGenresStringTest {

    private val genresListFourItems = listOf("Action", "Family", "Drama", "Crime")
    private val genresStringFourItems = "Action | Family | Drama | Crime"

    private val genresListOneItem = listOf("Action")
    private val genresStringOneItem = "Action"

    private val genresListEmpty = listOf<String>()
    private val genresStringEmpty = ""

    private lateinit var mapper: TvShowDetailedModelMapper

    @Before
    fun setUp() {
        mapper = TvShowDetailedModelMapper()
    }

    @Test
    fun test_genre_from_list_to_string_four() {
        assertEquals(genresStringFourItems, mapper.getGenresAsString(genresListFourItems))
    }

    @Test
    fun test_genre_from_list_to_string_one() {
        assertEquals(genresStringOneItem, mapper.getGenresAsString(genresListOneItem))
    }

    @Test
    fun test_genre_from_list_to_string_empty() {
        assertEquals(genresStringEmpty, mapper.getGenresAsString(genresListEmpty))
    }
}
