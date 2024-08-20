package com.example.feedapp.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "welcome_screen")

    val welcomeLabel: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[WELCOME_SCREEN_LABEL_KEY]
        }

    suspend fun saveWelcomeLabel(label: String) {
        context.dataStore.edit { preferences ->
            preferences[WELCOME_SCREEN_LABEL_KEY] = label
        }
    }

    companion object {
        val WELCOME_SCREEN_LABEL_KEY = stringPreferencesKey("feed_title_label")
    }
}