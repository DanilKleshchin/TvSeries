package com.danil.kleshchin.tvseries.data.popular.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danil.kleshchin.tvseries.data.popular.datasource.local.TvShowPopularEntityDatabase

@Entity(tableName = TvShowPopularEntityDatabase.tableName)
data class TvShowPopularDbEntity(
    val id: Int,

    @PrimaryKey(autoGenerate = true)
    val position: Long = 0,

    val name: String,

    val detailUrl: String,

    val startDate: String?,

    val country: String,

    val network: String,

    val status: String,

    val iconUrl: String,

    val pageNumber: Int
)
