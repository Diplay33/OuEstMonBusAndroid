package model.preferences_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreFirstLaunch(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("firstLaunchDone")
    }

    suspend fun enable() {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("firstLaunchDone")] = true
        }
    }

    /*suspend fun disable() {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("firstLaunchDone")] = false
        }
    }*/

    val isEnabled: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[booleanPreferencesKey("firstLaunchDone")]
    }
}