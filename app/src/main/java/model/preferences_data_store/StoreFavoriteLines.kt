package model.preferences_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreFavoriteLines(private val context: Context, private val lineId: String) {
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

    val isFavorite: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[intPreferencesKey(lineId)] == 1
    }
}