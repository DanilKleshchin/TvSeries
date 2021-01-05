package com.danil.kleshchin.tvseries.data

import com.danil.kleshchin.tvseries.data.popular.entity.TvShowPopularEntity
import com.danil.kleshchin.tvseries.data.popular.mapper.TvShowPopularDataMapper
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class TvShowPopularMapperTest {

    private val id = -1
    private val name = "testName"
    private val detailUrl = "http://some.com"
    private val startDate = "00:00:00"
    private val network = "The CW"
    private val country = "RU"
    private val status = "ended"
    private val iconUrl = "http://some.icon.com"

    private lateinit var tvShowPopular: TvShowPopular
    private lateinit var tvShowPopularEntity: TvShowPopularEntity
    private lateinit var tvShowPopularDataMapper: TvShowPopularDataMapper

    @Before
    fun setUp() {
        tvShowPopularDataMapper = TvShowPopularDataMapper()
        initTvShowPopularApi()
    }

    @Test
    fun test_popular_entity_happy() {
        tvShowPopular = tvShowPopularDataMapper.transform(tvShowPopularEntity)

        assertEquals(id, tvShowPopular.id)
        assertEquals(name, tvShowPopular.name)
        assertEquals(detailUrl, tvShowPopular.detailUrl)
        assertEquals(startDate, tvShowPopular.startDate)
        assertEquals(network, tvShowPopular.network)
        assertEquals(status, tvShowPopular.status)
        assertEquals(iconUrl, tvShowPopular.iconUrl)
        assertEquals(country, tvShowPopular.country)
    }

    private fun initTvShowPopularApi() {
        tvShowPopularEntity = TvShowPopularEntity(
            id,
            name,
            detailUrl,
            startDate,
            country,
            network,
            status,
            iconUrl
        )
    }
}
