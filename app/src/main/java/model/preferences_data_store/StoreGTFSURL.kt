package model.preferences_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreGTFSURL(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("gtfs_url")
    }

    suspend fun setGTFSTripsURL(url: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey("gtfs_trips_url")] = url
        }
    }

    val gtfsTripsURL: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[stringPreferencesKey("gtfs_trips_url")]
    }
}