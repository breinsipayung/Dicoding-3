package com.example.brein.mysubmission3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.brein.mysubmission3.local.DatabaseUser
import com.example.brein.mysubmission3.local.FavoriteUser
import com.example.brein.mysubmission3.local.FavoriteUserDao

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao : FavoriteUserDao?
    private var userDatabase: DatabaseUser? = DatabaseUser.getDatabase(application)

    init {
        userDao = userDatabase?.favoriteUserDao()
    }

    fun getFavorite() : LiveData<List<FavoriteUser>>?{
        return userDao?.getFavorite()
    }


}