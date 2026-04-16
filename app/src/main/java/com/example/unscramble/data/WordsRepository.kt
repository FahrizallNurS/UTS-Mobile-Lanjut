package com.example.unscramble.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "words_pref")

class WordsRepository(private val context: Context) {

    companion object{
        val CUSTOM_WORDS_KEY = stringSetPreferencesKey("custom_words")
    }

val customWordsFlow: Flow<Set<String>> = context.dataStore.data
    .map { preferences ->
        preferences[CUSTOM_WORDS_KEY] ?: emptySet()
    }

    suspend fun addWord(word: String) {
        context.dataStore.edit { preferences ->
            val current = preferences[CUSTOM_WORDS_KEY] ?: emptySet()
            preferences[CUSTOM_WORDS_KEY] = current + word.trim().lowercase()
        }
    }
}