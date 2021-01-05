package com.danil.kleshchin.tvseries.data.popular.entity

import com.google.gson.annotations.SerializedName

data class TvShowPopularEntity(
    val id: Int,
    val name: String,

    @SerializedName("permalink")
    val detailUrl: String,

    @SerializedName("start-date")
    val startDate: String,

    val country: String,
    val network: String,
    val status: String,

    @SerializedName("image_thumbnail_path")
    val iconUrl: String
)
