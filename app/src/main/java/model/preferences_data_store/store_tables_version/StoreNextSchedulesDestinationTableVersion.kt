package model.preferences_data_store.store_tables_version;

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreNextSchedulesDestinationTableVersion(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore("nextSchedulesDestinationTableVersion")
    }

    suspend fun setCurrentVersion(version: String) {
        context.dataStore.edit { preferences ->
            preferences[
                stringPreferencesKey("nextSchedulesDestinationTableVersion")
            ] = version
        }
    }

    val version: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[stringPreferencesKey("nextSchedulesDestinationTableVersion")]
    }
}