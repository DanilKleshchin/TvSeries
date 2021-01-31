package com.danil.kleshchin.tvseries.data.popular.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danil.kleshchin.tvseries.data.popular.entity.TvShowPopularEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface TvShowPopularEntityDao {

    @Query("SELECT * FROM TV_SHOW_POPULAR")
    fun getTvShowPopularList(): Observable<List<TvShowPopularEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowPopularList(tvShowPopularList: List<TvShowPopularEntity>): Completable

    @Query("DELETE FROM TV_SHOW_POPULAR")
    fun removeAll(): Completable
}
