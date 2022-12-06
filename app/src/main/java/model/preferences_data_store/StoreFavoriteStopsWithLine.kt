package model.preferences_data_store

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

//key: lineId, value: stationId(s)
class StoreFavoriteStopsWithLine(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("favoriteStopsWithLine")
    }

    suspend fun saveFavoriteStopForLine(lineId: String, stopId: String) {
        context.dataStore.edit { preferences ->
            preferences[stringSetPreferencesKey(lineId)] = preferences[stringSetPreferencesKey(lineId)]?.plus(stopId) ?: setOf(stopId)
        }
    }

    suspend fun removeFavoriteStopForLine(lineId: String, stopId: String) {
        context.dataStore.edit { preferences ->
            preferences[stringSetPreferencesKey(lineId)] = preferences[stringSetPreferencesKey(lineId)]?.minus(stopId) ?: setOf()
        }
    }

    suspend fun getFavoriteStopsForLine(lineId: String, callback: (Set<String>) -> Unit) {
        context.dataStore.data.collect { preferences ->
            callback(preferences[stringSetPreferencesKey(lineId)] ?: setOf())
        }
    }
}