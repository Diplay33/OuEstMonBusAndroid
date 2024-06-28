package model.preferences_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreChosenNetwork(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("chosenNetwork")
    }

    suspend fun setNetwork(network: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey("chosenNetwork")] = network
        }
    }

    val chosenNetwork: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[stringPreferencesKey("chosenNetwork")]
    }
}