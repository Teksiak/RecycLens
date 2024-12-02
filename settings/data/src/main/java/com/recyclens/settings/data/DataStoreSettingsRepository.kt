package com.recyclens.settings.data

import android.app.LocaleManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.recyclens.core.domain.settings.Language
import com.recyclens.core.domain.settings.SettingsRepository
import com.recyclens.core.domain.settings.SettingsRepository.Companion.DATASTORE_NAME
import com.recyclens.core.domain.settings.SettingsRepository.Companion.DEFAULT_HISTORY_SIZE
import com.recyclens.core.domain.settings.SettingsRepository.Companion.DEFAULT_LANGUAGE
import com.recyclens.core.domain.util.DataError
import com.recyclens.core.domain.util.EmptyResult
import com.recyclens.core.domain.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class DataStoreSettingsRepository @Inject constructor(
    @ApplicationContext
    private val applicationContext: Context,
    private val applicationScope: CoroutineScope
): SettingsRepository {

    private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATASTORE_NAME
    )

    init {
        applicationScope.launch {
            if(applicationContext.settingsDataStore.data.first() == emptyPreferences()) {
                setInitialLanguageSetting()
            }
        }
    }

    override val language: Flow<Language>
        get() = applicationContext.settingsDataStore.data
            .catch {
                if(it is Exception) {
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map { preferences ->
                preferences[LANGUAGE_KEY]?.let { Language.fromTag(it) } ?: DEFAULT_LANGUAGE
            }


    override val historySize: Flow<Int>
        get() = applicationContext.settingsDataStore.data
            .catch {
                if(it is Exception) {
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map { preferences ->
                preferences[HISTORY_SIZE_KEY] ?: DEFAULT_HISTORY_SIZE
            }

    override suspend fun setLanguage(language: Language): EmptyResult<DataError.Local> {
        return safeSettingsChange {
            if(!language.isAvailable) return@safeSettingsChange
            applicationContext.settingsDataStore.edit { preferences ->
                preferences[LANGUAGE_KEY] = language.tag
            }
            changeApplicationLocale(language)
        }
    }

    override suspend fun setHistorySize(size: Int): EmptyResult<DataError.Local> {
        return safeSettingsChange {
            applicationContext.settingsDataStore.edit { preferences ->
                preferences[HISTORY_SIZE_KEY] = size
            }
        }
    }

    private suspend fun safeSettingsChange(action: suspend () -> Unit): EmptyResult<DataError.Local> {
        return try {
            action()
            Result.Success(Unit)
        } catch (e: IOException) {
            Result.Error(DataError.Local.UNKNOWN_ERROR)
        }
    }

    private fun changeApplicationLocale(language: Language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            applicationContext.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(language.tag)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(language.tag)
            )
        }
    }

    private suspend fun setInitialLanguageSetting() {
        val locales = Resources.getSystem().configuration.locales
        val systemAvailableLanguage = (0 until locales.size()).firstNotNullOfOrNull { index ->
            Language.fromLocale(locales[index])
        }
        val initialLanguage = systemAvailableLanguage ?: DEFAULT_LANGUAGE
        safeSettingsChange {
            applicationContext.settingsDataStore.edit { preferences ->
                preferences[LANGUAGE_KEY] = initialLanguage.tag
            }
        }
    }

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private val HISTORY_SIZE_KEY = intPreferencesKey("history_size")
    }
}