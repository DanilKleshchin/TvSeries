package com.danil.kleshchin.tvseries.domain.entity

data class TvShowPopular(
        val id: Int,
        val name: String,
        val detailUrl: String,
        val startDate: String,
        val network: String,
        val status: String,
        val iconUrl: String
)
