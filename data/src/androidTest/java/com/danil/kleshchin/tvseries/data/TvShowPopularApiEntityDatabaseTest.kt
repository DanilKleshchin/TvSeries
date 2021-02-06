package com.danil.kleshchin.tvseries.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.danil.kleshchin.tvseries.data.popular.datasource.local.TvShowPopularEntityDao
import com.danil.kleshchin.tvseries.data.popular.datasource.local.TvShowPopularEntityDatabase
import com.danil.kleshchin.tvseries.data.popular.datasource.local.entity.TvShowPopularDbEntity
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TvShowPopularApiEntityDatabaseTest {

    private val id = -1
    private val name = "testName"
    private val detailUrl = "http://some.com"
    private val startDate = "00:00:00"
    private val network = "The CW"
    private val country = "RU"
    private val status = "ended"
    private val iconUrl = "http://some.icon.com"
    private val pageNumber = 1

    private lateinit var tvShowPopularDbList: List<TvShowPopularDbEntity>
    private lateinit var tvShowPopularDbEntity: TvShowPopularDbEntity
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

        initTvShowPopularDbEntity()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTvShowPopularDbEntity() {
        tvShowPopularEntityDao.insertTvShowPopularList(tvShowPopularDbList)
        tvShowPopularEntityDao.getTvShowPopularListByPageNumber(pageNumber)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                assertEquals(id, tvShowPopularDbEntity.id)
                assertEquals(name, tvShowPopularDbEntity.name)
                assertEquals(detailUrl, tvShowPopularDbEntity.detailUrl)
                assertEquals(startDate, tvShowPopularDbEntity.startDate)
                assertEquals(country, tvShowPopularDbEntity.country)
                assertEquals(network, tvShowPopularDbEntity.network)
                assertEquals(status, tvShowPopularDbEntity.status)
                assertEquals(iconUrl, tvShowPopularDbEntity.iconUrl)
            }
            .subscribe {
                tvShowPopularDbEntity = it[0]
            }
    }

    private fun initTvShowPopularDbEntity() {
        tvShowPopularDbList = arrayListOf(
            TvShowPopularDbEntity(
                id,
                name = name,
                detailUrl = detailUrl,
                startDate = startDate,
                country = country,
                network = network,
                status = status,
                iconUrl = iconUrl,
                pageNumber = pageNumber
            )
        )
    }
}
