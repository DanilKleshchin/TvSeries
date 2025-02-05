package com.danil.kleshchin.tvseries.domain.entity

data class TvShowDetailed(
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
        val genres: List<String>,
        val episodesPictures: List<String>
)
