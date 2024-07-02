package model.preferences_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreVersionCheck(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("versionCheck")
    }

    suspend fun markVersionAsChecked(version: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(version)] = "ok"
        }
    }

    suspend fun unmarkVersionAsChecked(version: String) {
        context.dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(version))
        }
    }

    fun versionIsChecked(version: String): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(version)] == "ok"
        }
    }
}