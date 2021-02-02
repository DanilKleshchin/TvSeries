package com.danil.kleshchin.tvseries.screens.popular

class TvShowPopularState(
    private val pageNumber: Int,
    private val pagesCount: Int
) : TvShowPopularContract.State {

    override fun getCurrentPageNumber(): Int = pageNumber

    override fun getPagesCount(): Int = pagesCount
}
