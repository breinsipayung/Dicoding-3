package com.example.brein.mysubmission3.setting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val KEY = booleanPreferencesKey("theme")

    companion object{
        @Volatile
        private var INSTANCE: SettingPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>) : SettingPreference{
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    fun getTheme(): Flow<Boolean> {
        return dataStore.data.map {
            it[KEY] ?: false
        }
    }

    suspend fun saveTheme(isDarkModeActive: Boolean){
        dataStore.edit {
            it[KEY] = isDarkModeActive
        }
    }

}