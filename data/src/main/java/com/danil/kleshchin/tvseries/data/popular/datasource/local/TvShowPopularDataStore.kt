package com.danil.kleshchin.tvseries.data.popular.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Used for storing tv show popular pages count
 */
class TvShowPopularDataStore @Inject constructor(
    context: Context
) {
    private val PREFERENCES_NAME = "TvShowPreferences"
    private val PAGES_COUNT_KEY = intPreferencesKey("pages_count")

    private val dataStore: DataStore<Preferences> = context.createDataStore(PREFERENCES_NAME)

    suspend fun setPagesCount(pagesCount: Int) {
        dataStore.edit {
            it[PAGES_COUNT_KEY] = pagesCount
        }
    }

    fun getPagesCount(): Flow<Int> = dataStore.data
        .map {
            it[PAGES_COUNT_KEY] ?: 0
        }
}
