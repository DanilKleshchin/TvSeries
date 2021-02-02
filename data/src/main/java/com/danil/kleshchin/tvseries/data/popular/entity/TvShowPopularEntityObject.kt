package com.danil.kleshchin.tvseries.data.popular.entity

import com.google.gson.annotations.SerializedName

data class TvShowPopularEntityObject(
    val page: Int,
    val pages: Int,

    @SerializedName("tv_shows") val tvShowList: List<TvShowPopularEntity>
)
