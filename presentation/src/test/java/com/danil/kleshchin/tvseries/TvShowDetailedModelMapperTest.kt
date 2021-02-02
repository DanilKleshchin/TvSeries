package com.danil.kleshchin.tvseries

import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed
import com.danil.kleshchin.tvseries.screens.detailed.models.TvShowDetailedModel
import com.danil.kleshchin.tvseries.screens.detailed.models.TvShowDetailedModelMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowDetailedModelMapperTest {

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
    private val runtime = 60
    private val youTubeUrl = null
    private val youTubeUrlUnknown = "Unknown"
    private val rating = 6.55123
    private val ratingDelta = 0.01
    private val ratingCount = 500
    private val genresList = listOf("Drama", "Action")
    private val genresString = "Drama | Action"
    private val episodesPictures = listOf("http://some.icon1.com", "http://some.icon2.com")

    private lateinit var tvShowDetailed: TvShowDetailed
    private lateinit var tvShowDetailedModel: TvShowDetailedModel
    private lateinit var tvShowDetailedDataMapper: TvShowDetailedModelMapper

    @Before
    fun setUp() {
        tvShowDetailedDataMapper = TvShowDetailedModelMapper()
        initTvShowPopularApi()
    }

    @Test
    fun test_detailed_entity_happy() {
        tvShowDetailedModel = tvShowDetailedDataMapper.transform(tvShowDetailed)

        Assert.assertEquals(id, tvShowDetailedModel.id)
        Assert.assertEquals(name, tvShowDetailedModel.name)
        Assert.assertEquals(pageUrl, tvShowDetailedModel.pageUrl)
        Assert.assertEquals(description, tvShowDetailedModel.description)
        Assert.assertEquals(moreDescriptionUrl, tvShowDetailedModel.moreDescriptionUrl)
        Assert.assertEquals(startDate, tvShowDetailedModel.startDate)
        Assert.assertEquals(country, tvShowDetailedModel.country)
        Assert.assertEquals(status, tvShowDetailedModel.status)
        Assert.assertEquals(network, tvShowDetailedModel.network)
        Assert.assertEquals(youTubeUrlUnknown, tvShowDetailedModel.youTubeUrl)
        Assert.assertEquals(iconUrl, tvShowDetailedModel.iconUrl)
        Assert.assertEquals(rating, tvShowDetailedModel.rating, ratingDelta)
        Assert.assertEquals(ratingCount, tvShowDetailedModel.ratingCount)
        Assert.assertEquals(genresString, tvShowDetailedModel.genres)
        Assert.assertEquals(episodesPictures, tvShowDetailedModel.episodesPictures)
    }

    private fun initTvShowPopularApi() {
        tvShowDetailed = TvShowDetailed(
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
            runtime,
            genresList,
            episodesPictures
        )
    }
}
