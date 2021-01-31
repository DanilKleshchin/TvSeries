package com.danil.kleshchin.tvseries.data.popular.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danil.kleshchin.tvseries.data.popular.datasource.local.TvShowPopularEntityDatabase
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = TvShowPopularEntityDatabase.tableName)
data class TvShowPopularEntity(
    val id: Int,

    val name: String,

    @SerializedName("permalink")
    val detailUrl: String,

    @SerializedName("start_date")
    val startDate: String,

    val country: String,

    val network: String,

    val status: String,

    @SerializedName("image_thumbnail_path")
    val iconUrl: String,

    @PrimaryKey(autoGenerate = true)
    val position: Int = 0
) : Serializable
