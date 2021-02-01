package com.danil.kleshchin.tvseries.data.popular.mapper

import com.danil.kleshchin.tvseries.data.popular.entity.TvShowPopularEntity
import com.danil.kleshchin.tvseries.data.popular.entity.TvShowPopularEntityObject
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import javax.inject.Inject

class TvShowPopularDataMapper @Inject constructor() {

    private val UNKNOWN_STUB = "Unknown"

    fun transform(tvShowPopularEntity: TvShowPopularEntity): TvShowPopular {
        val id = tvShowPopularEntity.id
        val name = tvShowPopularEntity.name
        val detailUrl = tvShowPopularEntity.detailUrl
        val startDate = tvShowPopularEntity.startDate ?: UNKNOWN_STUB
        val network = tvShowPopularEntity.network
        val country = tvShowPopularEntity.country
        val status = tvShowPopularEntity.status
        val iconUrl = tvShowPopularEntity.iconUrl
        return TvShowPopular(id, name, detailUrl, startDate, country, network, status, iconUrl)
    }

    fun transform(tvShowPopularEntityList: List<TvShowPopularEntity>): List<TvShowPopular> {
        val tvShowPopularList = arrayListOf<TvShowPopular>()
        for (tvShowPopularApi in tvShowPopularEntityList) {
            val tvShowPopular = transform(tvShowPopularApi)
            tvShowPopularList.add(tvShowPopular)
        }
        return tvShowPopularList
    }

    fun transform(tvShowPopularApiResponse: TvShowPopularEntityObject): List<TvShowPopular> {
        return transform(tvShowPopularApiResponse.tvShowList)
    }
}
