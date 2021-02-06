package com.danil.kleshchin.tvseries.data.popular.datasource.network.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TvShowPopularApiEntity(
    val id: Int,

    val name: String,

    @SerializedName("permalink")
    val detailUrl: String,

    @SerializedName("start_date")
    val startDate: String?,

    val country: String,

    val network: String,

    val status: String,

    @SerializedName("image_thumbnail_path")
    val iconUrl: String,
) : Serializable
