package com.danil.kleshchin.tvseries.data.popular.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danil.kleshchin.tvseries.data.popular.datasource.local.entity.TvShowPopularDbEntity

@Database(entities = [TvShowPopularDbEntity::class], version = 1, exportSchema = false)
abstract class TvShowPopularEntityDatabase : RoomDatabase() {

    abstract val tvShowPopularEntityDao: TvShowPopularEntityDao

    companion object {
        const val tableName = "TV_SHOW_POPULAR"

        @Volatile
        private var INSTANCE: TvShowPopularEntityDatabase? = null

        fun getInstance(context: Context): TvShowPopularEntityDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TvShowPopularEntityDatabase::class.java,
                        tableName
                    )
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
