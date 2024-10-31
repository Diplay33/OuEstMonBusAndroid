package model.preferences_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import model.DTO.Lines

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

    suspend fun checkMaxStopCountReached(callback: (Boolean) -> Unit) {
        context.dataStore.data.collect { preferences ->
            val favoriteStopsSet = mutableSetOf<String>()
            Lines.getAllLines().forEach {  line ->
                favoriteStopsSet.addAll(preferences[stringSetPreferencesKey(line.id.toString())]?.toList() ?: listOf())
            }
            callback(favoriteStopsSet.count() >= 10 )
        }
    }

    suspend fun getFavoriteStopsForLine(lineId: String, callback: (Set<String>) -> Unit) {
        context.dataStore.data.collect { preferences ->
            callback(preferences[stringSetPreferencesKey(lineId)] ?: setOf())
        }
    }
}