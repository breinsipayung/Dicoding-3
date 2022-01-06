package com.example.brein.mysubmission3.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brein.mysubmission3.api.RetrofitClient
import com.example.brein.mysubmission3.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel(){
    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setFollowing(login : String){
        RetrofitClient.api.getFollowing(login).enqueue(object : Callback<ArrayList<User>>{
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if(response.isSuccessful){
                    listFollowing.postValue(response.body())
                    Log.d(TAG, "onResponse : ${response.body()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

        })
    }

    fun getFollowing() : LiveData<ArrayList<User>>{
        return listFollowing
    }
}