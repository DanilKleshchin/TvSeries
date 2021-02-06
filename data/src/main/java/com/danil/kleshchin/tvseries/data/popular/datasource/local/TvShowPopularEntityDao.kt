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

    @Query("SELECT * FROM TV_SHOW_POPULAR WHERE pageNumber = :pageNumber")
    fun getTvShowPopularListByPageNumber(pageNumber: Int): Observable<List<TvShowPopularDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowPopularList(tvShowPopularList: List<TvShowPopularDbEntity>): Completable

    @Query("DELETE FROM TV_SHOW_POPULAR WHERE pageNumber = :pageNumber")
    fun removeTvShowPopularByPage(pageNumber: Int): Completable

    @Query("DELETE FROM TV_SHOW_POPULAR")
    fun removeAll(): Completable
}
