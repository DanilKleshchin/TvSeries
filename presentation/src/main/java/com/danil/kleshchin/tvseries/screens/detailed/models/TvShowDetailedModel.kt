package com.danil.kleshchin.tvseries.screens.detailed.models

data class TvShowDetailedModel(
    val id: Int,
    val name: String,
    val pageUrl: String,
    val description: String,
    val moreDescriptionUrl: String?,
    val startDate: String,
    val country: String,
    val status: String,
    val network: String,
    val youTubeUrl: String?,
    val iconUrl: String,
    val rating: Double,
    val ratingCount: Int,
    val runtime: Int,
    val genres: String,
    val episodesPictures: List<String>
)
