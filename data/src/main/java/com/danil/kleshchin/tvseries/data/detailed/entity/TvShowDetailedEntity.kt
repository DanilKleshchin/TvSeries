package com.danil.kleshchin.tvseries.data.detailed.entity

import com.google.gson.annotations.SerializedName

data class TvShowDetailedEntity(
    val id: Int,
    val name: String,

    @SerializedName("url")
    val pageUrl: String,

    val description: String,

    @SerializedName("description_source")
    val descriptionUrl: String,

    @SerializedName("start_date")
    val startDate: String,

    val country: String,
    val status: String,
    val network: String,

    @SerializedName("youtube_link")
    val youTubeUrl: String?,

    @SerializedName("image_path")
    val iconUrl: String,

    val rating: Double,

    @SerializedName("rating_count")
    val ratingCount: Int,

    @SerializedName("genres")
    val genreList: List<String>,

    @SerializedName("pictures")
    val episodesPictures: List<String>
)
