package com.danil.kleshchin.tvseries.data

import com.danil.kleshchin.tvseries.data.detailed.entity.TvShowDetailedEntity
import com.danil.kleshchin.tvseries.data.detailed.mapper.TvShowDetailedDataMapper
import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TvShowDetailedMapperTest {

    private val id = -1
    private val name = "testName"
    private val startDate = "00:00:00"
    private val network = "The CW"
    private val country = "RU"
    private val status = "ended"
    private val iconUrl = "http://some.icon.com"
    private val pageUrl = "http://full.page"
    private val description = "Some description"
    private val moreDescriptionUrl = "http://more.com"
    private val youTubeUrl = null
    private val rating = 6.5
    private val ratingDelta = 0.0001
    private val ratingCount = 500
    private val genres = listOf("Drama", "Action")
    private val episodesPictures = listOf("http://some.icon1.com", "http://some.icon2.com")

    private lateinit var tvShowDetailed: TvShowDetailed
    private lateinit var tvShowDetailedEntity: TvShowDetailedEntity
    private lateinit var tvShowDetailedDataMapper: TvShowDetailedDataMapper

    @Before
    fun setUp() {
        tvShowDetailedDataMapper = TvShowDetailedDataMapper()
        initTvShowPopularApi()
    }

    @Test
    fun test_detailed_entity_happy() {
        tvShowDetailed = tvShowDetailedDataMapper.transform(tvShowDetailedEntity)

        assertEquals(id, tvShowDetailed.id)
        assertEquals(name, tvShowDetailed.name)
        assertEquals(pageUrl, tvShowDetailed.pageUrl)
        assertEquals(description, tvShowDetailed.description)
        assertEquals(moreDescriptionUrl, tvShowDetailed.moreDescriptionUrl)
        assertEquals(startDate, tvShowDetailed.startDate)
        assertEquals(country, tvShowDetailed.country)
        assertEquals(status, tvShowDetailed.status)
        assertEquals(network, tvShowDetailed.network)
        assertEquals(youTubeUrl, tvShowDetailed.youTubeUrl)
        assertEquals(iconUrl, tvShowDetailed.iconUrl)
        assertEquals(rating, tvShowDetailed.rating, ratingDelta)
        assertEquals(ratingCount, tvShowDetailed.ratingCount)
        assertEquals(genres, tvShowDetailed.genres)
        assertEquals(episodesPictures, tvShowDetailed.episodesPictures)
    }

    private fun initTvShowPopularApi() {
        tvShowDetailedEntity = TvShowDetailedEntity(
            id,
            name,
            pageUrl,
            description,
            moreDescriptionUrl,
            startDate,
            country,
            status,
            network,
            youTubeUrl,
            iconUrl,
            rating,
            ratingCount,
            genres,
            episodesPictures
        )
    }
}
