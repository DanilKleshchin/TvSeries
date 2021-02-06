package com.danil.kleshchin.tvseries.data.popular.datasource.network.entity

import com.google.gson.annotations.SerializedName

data class TvShowPopularApiEntityObject(
    val page: Int,
    val pages: Int,

    @SerializedName("tv_shows") val tvShowListApi: List<TvShowPopularApiEntity>
)
