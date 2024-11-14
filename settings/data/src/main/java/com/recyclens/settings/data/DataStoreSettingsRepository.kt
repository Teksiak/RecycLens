package com.recyclens.settings.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.recyclens.core.domain.settings.Language
import com.recyclens.core.domain.settings.SettingsRepository
import com.recyclens.core.domain.settings.SettingsRepository.Companion.DATASTORE_NAME
import com.recyclens.core.domain.util.DataError
import com.recyclens.core.domain.util.EmptyResult
import com.recyclens.core.domain.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreSettingsRepository @Inject constructor(
    @ApplicationContext
    private val applicationContext: Context
): SettingsRepository {
    private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATASTORE_NAME
    )

    // ZRÓB TAK JAK W NUTRILIGHT NAZWY PLIKÓW TE SAME
    // DODAJ JESZCZE IKONĘ APKI TAKĄ JAK W ABOUT US
    // JAK SKOŃCZYSZ TO ZACZNIJ ROBIĆ OPIS TEGO PROJEKTU

    override val language: Flow<Language>
        // tu jest typ flowa Language a w datastore można trzymać proste typy, więc
        // mapuj tego string który jest w datastore na enuma Language.valueOf(tu string z datastore)
        // analogicznie w setLanguage zapisujesz Language.name
        get() = TODO("Not yet implemented")


    override val historySize: Flow<Int>
        get() = TODO("Not yet implemented")

    override suspend fun setLanguage(language: Language): EmptyResult<DataError.Local> {
        // Przed zapisaniem sprawdź czy isAvailable jest true jeżeli nie to nie zapisuj ale nie rzucaj errora
        TODO("Not yet implemented")
    }

    override suspend fun setHistorySize(size: Int): EmptyResult<DataError.Local> {
        TODO("Not yet implemented")
    }

    private suspend fun safeSettingsChange(action: suspend () -> Unit): EmptyResult<DataError.Local> {
        return try {
            action()
            Result.Success(Unit)
        } catch (e: IOException) {
            Result.Error(DataError.Local.UNKNOWN_ERROR)
        }
    }

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private val HISTORY_SIZE_KEY = intPreferencesKey("history_size")
    }
}