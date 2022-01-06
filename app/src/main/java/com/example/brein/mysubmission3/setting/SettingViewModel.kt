package com.example.brein.mysubmission3.setting

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : ViewModel() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
    private val preferences: SettingPreference = SettingPreference.getInstance(application.dataStore)

    fun getTheme(): LiveData<Boolean> {
        return preferences.getTheme().asLiveData()
    }

    fun saveTheme(isDarkModeActive : Boolean){
        viewModelScope.launch {
            preferences.saveTheme(isDarkModeActive)
        }
    }
}