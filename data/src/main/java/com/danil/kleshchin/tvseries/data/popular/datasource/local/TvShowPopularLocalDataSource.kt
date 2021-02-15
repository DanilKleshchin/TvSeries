package com.danil.kleshchin.tvseries.data.popular.datasource.local

import com.danil.kleshchin.tvseries.data.popular.datasource.local.entity.TvShowPopularDbEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface TvShowPopularLocalDataSource {

    fun getTvShowPopularEntityListUpToPageNumberInclusive(
        pageNumber: Int
    ): Observable<List<TvShowPopularDbEntity>>

    fun getTvShowPopularEntityListByPageNumber(
        pageNumber: Int
    ): Observable<List<TvShowPopularDbEntity>>

    fun getTvShowPopularEntityList(): Observable<List<TvShowPopularDbEntity>>

    fun insertTvShowPopularEntityList(list: List<TvShowPopularDbEntity>): Completable

    fun removeTvShowPopularByPage(pageNumber: Int): Completable

    fun removeAll(): Completable
}
