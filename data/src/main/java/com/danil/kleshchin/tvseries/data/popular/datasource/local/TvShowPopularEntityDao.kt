package com.danil.kleshchin.tvseries.data.popular.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danil.kleshchin.tvseries.data.popular.datasource.local.entity.TvShowPopularDbEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface TvShowPopularEntityDao {

    @Query("SELECT * FROM TV_SHOW_POPULAR ORDER BY position")
    fun getTvShowPopularList(): Observable<List<TvShowPopularDbEntity>>

    @Query("SELECT * FROM TV_SHOW_POPULAR WHERE pageNumber <= :pageNumber ORDER BY position")
    fun getTvShowPopularEntityListUpToPageNumberInclusive(
        pageNumber: Int
    ): Observable<List<TvShowPopularDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowPopularList(tvShowPopularList: List<TvShowPopularDbEntity>): Completable

    @Query("DELETE FROM TV_SHOW_POPULAR WHERE pageNumber = :pageNumber")
    fun removeTvShowPopularByPage(pageNumber: Int): Completable

    @Query("DELETE FROM TV_SHOW_POPULAR")
    fun removeAll(): Completable
}
