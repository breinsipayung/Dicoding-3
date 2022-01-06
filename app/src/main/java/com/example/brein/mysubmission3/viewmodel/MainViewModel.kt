package com.example.brein.mysubmission3.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.brein.mysubmission3.api.RetrofitClient
import com.example.brein.mysubmission3.model.User
import com.example.brein.mysubmission3.model.UserResponse
import com.example.brein.mysubmission3.setting.SettingPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : ViewModel() {
    val listMain = MutableLiveData<ArrayList<User>>()

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val pref: SettingPreference = SettingPreference.getInstance(application.dataStore)

    fun setSearch(login: String) {
        RetrofitClient.api
            .getSearchUser(login)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listMain.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("onFailure", t.message!!)
                }
            })
    }

    fun getSearch(): LiveData<ArrayList<User>> {
        return listMain
    }

    fun getTheme(): LiveData<Boolean> = pref.getTheme().asLiveData()

}