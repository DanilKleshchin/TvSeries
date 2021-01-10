package com.danil.kleshchin.tvseries.screens.detailed.models

import androidx.annotation.VisibleForTesting
import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed
import com.danil.kleshchin.tvseries.roundToTwoLastDigits
import javax.inject.Inject

class TvShowDetailedModelMapper @Inject constructor() {

    fun transform(tvShowDetailed: TvShowDetailed): TvShowDetailedModel {
        val youTubeUrl = tvShowDetailed.youTubeUrl ?: "Unknown"
        val ratingDouble = tvShowDetailed.rating.roundToTwoLastDigits()
        val genres = getGenresAsString(tvShowDetailed.genres)

        return TvShowDetailedModel(
            tvShowDetailed.id,
            tvShowDetailed.name,
            tvShowDetailed.pageUrl,
            tvShowDetailed.description,
            tvShowDetailed.moreDescriptionUrl,
            tvShowDetailed.startDate,
            tvShowDetailed.country,
            tvShowDetailed.status,
            tvShowDetailed.network,
            youTubeUrl,
            tvShowDetailed.iconUrl,
            ratingDouble,
            tvShowDetailed.ratingCount,
            tvShowDetailed.runtime,
            genres,
            tvShowDetailed.episodesPictures
        )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getGenresAsString(genreList: List<String>): String {
        var genres = ""
        if (genreList.isEmpty()) {
            return genres
        }
        genreList.forEach {
            genre -> genres += "$genre | "
        }
        return genres.dropLast(3) //Remove last two spaces and | sign
    }
}
