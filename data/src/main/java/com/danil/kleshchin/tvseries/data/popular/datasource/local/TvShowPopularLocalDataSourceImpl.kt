package com.danil.kleshchin.tvseries.data.popular.datasource.local

import com.danil.kleshchin.tvseries.data.popular.datasource.local.entity.TvShowPopularDbEntity
import io.reactivex.Completable
import io.reactivex.Observable

class TvShowPopularLocalDataSource(
    private val tvShowPopularEntityDatabase: TvShowPopularEntityDatabase
) {

    fun getTvShowPopularEntityListUpToPageNumberInclusive(
        pageNumber: Int
    ): Observable<List<TvShowPopularDbEntity>> {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao
            .getTvShowPopularEntityListUpToPageNumberInclusive(
                pageNumber
            )
    }

    fun getTvShowPopularEntityListByPageNumber(
        pageNumber: Int
    ): Observable<List<TvShowPopularDbEntity>> {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao
            .getTvShowPopularEntityListByPageNumber(
                pageNumber
            )
    }

    fun getTvShowPopularEntityList(): Observable<List<TvShowPopularDbEntity>> {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao.getTvShowPopularList()
    }

    fun insertTvShowPopularEntityList(list: List<TvShowPopularDbEntity>): Completable {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao.insertTvShowPopularList(list)
    }

    fun removeTvShowPopularByPage(pageNumber: Int): Completable {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao.removeTvShowPopularByPage(
            pageNumber
        )
    }

    fun removeAll(): Completable {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao.removeAll()
    }
}
