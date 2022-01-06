package com.example.brein.mysubmission3.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.brein.mysubmission3.api.RetrofitClient
import com.example.brein.mysubmission3.local.DatabaseUser
import com.example.brein.mysubmission3.local.FavoriteUser
import com.example.brein.mysubmission3.local.FavoriteUserDao
import com.example.brein.mysubmission3.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    val detailUser = MutableLiveData<DetailUserResponse>()

    private var userDao: FavoriteUserDao?
    private var userDatabase: DatabaseUser? = DatabaseUser.getDatabase(application)

    init {
        userDao = userDatabase?.favoriteUserDao()
    }

    fun setDetail(login: String) {
        RetrofitClient.api.getDetailUser(login).enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    detailUser.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d("onFailure ", t.message!!)
            }

        })
    }

    fun addFavorite(login: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var detail = FavoriteUser(login, id, avatarUrl)
            userDao?.addToFavorite(detail)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)


    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavorite(id)
        }
    }
}