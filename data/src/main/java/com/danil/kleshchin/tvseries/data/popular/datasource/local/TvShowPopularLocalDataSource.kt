package com.danil.kleshchin.tvseries.data.popular.datasource.local

import com.danil.kleshchin.tvseries.data.popular.entity.TvShowPopularEntity
import io.reactivex.Completable
import io.reactivex.Observable

class TvShowPopularLocalDataSource(
    private val tvShowPopularEntityDatabase: TvShowPopularEntityDatabase
) {

    fun getTvShowPopularEntityList(): Observable<List<TvShowPopularEntity>> {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao.getTvShowPopularList()
    }

    fun insertTvShowPopularEntityList(list: List<TvShowPopularEntity>): Completable {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao.insertTvShowPopularList(list)
    }

    fun removeAll(): Completable {
        return tvShowPopularEntityDatabase.tvShowPopularEntityDao.removeAll()
    }
}
