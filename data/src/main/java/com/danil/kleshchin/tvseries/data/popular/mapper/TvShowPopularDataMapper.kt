package com.danil.kleshchin.tvseries.data.popular.mapper

import com.danil.kleshchin.tvseries.data.popular.datasource.local.entity.TvShowPopularDbEntity
import com.danil.kleshchin.tvseries.data.popular.datasource.network.entity.TvShowPopularApiEntity
import com.danil.kleshchin.tvseries.data.popular.datasource.network.entity.TvShowPopularApiEntityObject
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import javax.inject.Inject

class TvShowPopularDataMapper @Inject constructor() {

    private val UNKNOWN_STUB = "Unknown"

    fun transform(tvShowPopularApiEntity: TvShowPopularApiEntity): TvShowPopular {
        val id = tvShowPopularApiEntity.id
        val name = tvShowPopularApiEntity.name
        val detailUrl = tvShowPopularApiEntity.detailUrl
        val startDate = tvShowPopularApiEntity.startDate ?: UNKNOWN_STUB
        val network = tvShowPopularApiEntity.network
        val country = tvShowPopularApiEntity.country
        val status = tvShowPopularApiEntity.status
        val iconUrl = tvShowPopularApiEntity.iconUrl
        return TvShowPopular(id, name, detailUrl, startDate, country, network, status, iconUrl)
    }

    fun transform(tvShowPopularEntity: TvShowPopularDbEntity): TvShowPopular {
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

    fun transform(
        tvShowPopularApiEntity: TvShowPopularApiEntity,
        pageNumber: Int
    ): TvShowPopularDbEntity {
        val id = tvShowPopularApiEntity.id
        val name = tvShowPopularApiEntity.name
        val detailUrl = tvShowPopularApiEntity.detailUrl
        val startDate = tvShowPopularApiEntity.startDate ?: UNKNOWN_STUB
        val network = tvShowPopularApiEntity.network
        val country = tvShowPopularApiEntity.country
        val status = tvShowPopularApiEntity.status
        val iconUrl = tvShowPopularApiEntity.iconUrl
        return TvShowPopularDbEntity(
            id,
            name = name,
            detailUrl = detailUrl,
            startDate = startDate,
            country = country,
            network = network,
            status = status,
            iconUrl = iconUrl,
            pageNumber = pageNumber
        )
    }

    fun transformApiEnityList(tvShowPopularApiEntityList: List<TvShowPopularApiEntity>): List<TvShowPopular> {
        val tvShowPopularList = arrayListOf<TvShowPopular>()
        for (tvShowPopularApi in tvShowPopularApiEntityList) {
            val tvShowPopular = transform(tvShowPopularApi)
            tvShowPopularList.add(tvShowPopular)
        }
        return tvShowPopularList
    }

    fun transformDbEntityList(tvShowPopularEntityList: List<TvShowPopularDbEntity>): List<TvShowPopular> {
        val tvShowPopularList = arrayListOf<TvShowPopular>()
        for (tvShowPopularApi in tvShowPopularEntityList) {
            val tvShowPopular = transform(tvShowPopularApi)
            tvShowPopularList.add(tvShowPopular)
        }
        return tvShowPopularList
    }

    fun transformApiToDbEntityList(
        tvShowApiApiEntityList: List<TvShowPopularApiEntity>,
        pageNumber: Int
    ): List<TvShowPopularDbEntity> {
        val tvShowPopularList = arrayListOf<TvShowPopularDbEntity>()
        for (tvShowPopularApi in tvShowApiApiEntityList) {
            val tvShowPopularDbEntity = transform(tvShowPopularApi, pageNumber)
            tvShowPopularList.add(tvShowPopularDbEntity)
        }
        return tvShowPopularList
    }

    fun transform(tvShowPopularApiApiResponse: TvShowPopularApiEntityObject): List<TvShowPopular> {
        return transformApiEnityList(tvShowPopularApiApiResponse.tvShowListApi)
    }
}
