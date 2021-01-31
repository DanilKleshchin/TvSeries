package com.danil.kleshchin.tvseries.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.danil.kleshchin.tvseries.data.popular.datasource.local.TvShowPopularEntityDao
import com.danil.kleshchin.tvseries.data.popular.datasource.local.TvShowPopularEntityDatabase
import com.danil.kleshchin.tvseries.data.popular.entity.TvShowPopularEntity
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TvShowPopularEntityDatabaseTest {

    private val id = -1
    private val name = "testName"
    private val detailUrl = "http://some.com"
    private val startDate = "00:00:00"
    private val network = "The CW"
    private val country = "RU"
    private val status = "ended"
    private val iconUrl = "http://some.icon.com"

    private lateinit var tvShowPopularList: List<TvShowPopularEntity>
    private lateinit var tvShowPopularEntity: TvShowPopularEntity
    private lateinit var tvShowPopularEntityDao: TvShowPopularEntityDao
    private lateinit var db: TvShowPopularEntityDatabase

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, TvShowPopularEntityDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        tvShowPopularEntityDao = db.tvShowPopularEntityDao

        initTvShowPopularApi()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        tvShowPopularEntityDao.insertTvShowPopularList(tvShowPopularList)
        tvShowPopularEntityDao.getTvShowPopularList()
            .subscribeOn(Schedulers.io())
            .doOnNext {
                assertEquals(id, tvShowPopularEntity.id)
                assertEquals(name, tvShowPopularEntity.name)
                assertEquals(detailUrl, tvShowPopularEntity.detailUrl)
                assertEquals(startDate, tvShowPopularEntity.startDate)
                assertEquals(country, tvShowPopularEntity.country)
                assertEquals(network, tvShowPopularEntity.network)
                assertEquals(status, tvShowPopularEntity.status)
                assertEquals(iconUrl, tvShowPopularEntity.iconUrl)
            }
            .subscribe {
                tvShowPopularEntity = it[0]
            }
    }

    private fun initTvShowPopularApi() {
        tvShowPopularList = arrayListOf(
            TvShowPopularEntity(
                id,
                name,
                detailUrl,
                startDate,
                country,
                network,
                status,
                iconUrl
            )
        )
    }
}
