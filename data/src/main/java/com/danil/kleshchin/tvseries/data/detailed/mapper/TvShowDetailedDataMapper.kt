package com.danil.kleshchin.tvseries.data.detailed.mapper

import com.danil.kleshchin.tvseries.data.detailed.entity.TvShowDetailedEntity
import com.danil.kleshchin.tvseries.data.detailed.entity.TvShowDetailedEntityObject
import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed
import javax.inject.Inject

class TvShowDetailedDataMapper @Inject constructor() {

    fun transform(tvShowDetailedEntity: TvShowDetailedEntity): TvShowDetailed {
        val id = tvShowDetailedEntity.id
        val name = tvShowDetailedEntity.name
        val pageUrl = tvShowDetailedEntity.pageUrl
        val description = tvShowDetailedEntity.description
        val descriptionUrl = tvShowDetailedEntity.descriptionUrl
        val startDate = tvShowDetailedEntity.startDate
        val network = tvShowDetailedEntity.network
        val country = tvShowDetailedEntity.country
        val status = tvShowDetailedEntity.status
        val youTubeUrl = tvShowDetailedEntity.youTubeUrl
        val iconUrl = tvShowDetailedEntity.iconUrl
        val rating = tvShowDetailedEntity.rating
        val ratingCount = tvShowDetailedEntity.ratingCount
        val genres = tvShowDetailedEntity.genreList
        val episodesPictures = tvShowDetailedEntity.episodesPictures
        val runtime = tvShowDetailedEntity.runtime
        return TvShowDetailed(
            id, name, pageUrl, description, descriptionUrl, startDate, country, status,
            network, youTubeUrl, iconUrl, rating, ratingCount, runtime, genres, episodesPictures
        )
    }

    fun transform(tvShowPopularApiResponse: TvShowDetailedEntityObject): TvShowDetailed {
        return transform(tvShowPopularApiResponse.tvShowList)
    }

}
