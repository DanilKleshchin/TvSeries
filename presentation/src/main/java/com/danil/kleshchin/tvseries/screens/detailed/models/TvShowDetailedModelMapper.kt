package com.danil.kleshchin.tvseries.screens.detailed.models

import androidx.annotation.VisibleForTesting
import androidx.core.text.HtmlCompat
import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed
import javax.inject.Inject

class TvShowDetailedModelMapper @Inject constructor() {

    fun transform(tvShowDetailed: TvShowDetailed): TvShowDetailedModel {
        val description =
            HtmlCompat.fromHtml(tvShowDetailed.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toString()
        val youTubeUrl = tvShowDetailed.youTubeUrl ?: "Unknown"
        val ratingString = String.format("%.2f", tvShowDetailed.rating)
        val ratingDouble = ratingString.toDouble()
        val genres = getGenresAsString(tvShowDetailed.genres)

        return TvShowDetailedModel(
            tvShowDetailed.id,
            tvShowDetailed.name,
            tvShowDetailed.pageUrl,
            description,
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
        for (genre in genreList) {
            genres += "$genre | "
        }
        return genres.substring(0, genres.length - 3) //Remove last two spaces and | sign
    }
}
