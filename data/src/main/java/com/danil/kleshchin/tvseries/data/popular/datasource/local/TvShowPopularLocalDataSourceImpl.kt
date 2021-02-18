package com.danil.kleshchin.tvseries.data.popular.datasource.local

import com.danil.kleshchin.tvseries.data.popular.datasource.local.entity.TvShowPopularDbEntity
import io.reactivex.Completable
import io.reactivex.Observable

class TvShowPopularLocalDataSourceImpl (
    private val tvShowPopularEntityDatabase: TvShowPopularEntityDatabase
) : TvShowPopularLocalDataSource {

    override fun getTvShowPopularEntityListUpToPageNumberInclusive(
        pageNumber: Int
    ): Observable<List<TvShowPopularDbEntity>> {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao
            .getTvShowPopularEntityListUpToPageNumberInclusive(
                pageNumber
            )
    }

    override fun getTvShowPopularEntityListByPageNumber(
        pageNumber: Int
    ): Observable<List<TvShowPopularDbEntity>> {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao
            .getTvShowPopularEntityListByPageNumber(
                pageNumber
            )
    }

    override fun getTvShowPopularEntityList(): Observable<List<TvShowPopularDbEntity>> {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao.getTvShowPopularList()
    }

    override fun insertTvShowPopularEntityList(list: List<TvShowPopularDbEntity>): Completable {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao.insertTvShowPopularList(list)
    }

    override fun removeTvShowPopularByPage(pageNumber: Int): Completable {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao.removeTvShowPopularByPage(
            pageNumber
        )
    }

    override fun removeAll(): Completable {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao.removeAll()
    }
}
