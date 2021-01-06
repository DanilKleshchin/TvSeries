package com.danil.kleshchin.tvseries.data.detailed.entity

import com.google.gson.annotations.SerializedName

data class TvShowDetailedEntityObject(
    @SerializedName("tvShow")
    val tvShowList: TvShowDetailedEntity
)

