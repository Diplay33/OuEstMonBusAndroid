package model.preferences_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class StoreFavoriteLines(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("favoriteLines")
    }

    suspend fun saveFavoriteLine(lineId: String) {
        context.dataStore.edit { preferences ->
            preferences[intPreferencesKey(lineId)] = 1
        }
    }

    suspend fun removeFromFavorites(lineId: String) {
        context.dataStore.edit { preferences ->
            preferences[intPreferencesKey(lineId)] = 0
        }
    }

    fun isFavorite(lineId: String, callback: (Boolean) -> Unit) {
        context.dataStore.data.map { preferences ->
            println("LOLOLOLLOLLOOLOLLOLLOOLOLOLOLOLOLOLOL")
            callback(preferences[intPreferencesKey(lineId)] == 1)
        }
    }
}