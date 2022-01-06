package com.example.brein.mysubmission3.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.brein.mysubmission3.setting.*
import com.example.brein.mysubmission3.viewmodel.*
import java.lang.IllegalArgumentException

class ViewModelFactory constructor(private val application: Application): ViewModelProvider.NewInstanceFactory(){
    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory{
            if(INSTANCE == null){
                synchronized(ViewModelFactory::class.java){
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> SettingViewModel(application) as T

            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(application) as T

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(application) as T

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(application) as T

            modelClass.isAssignableFrom(FollowingViewModel::class.java) -> FollowingViewModel() as T

            modelClass.isAssignableFrom(FollowerViewModel::class.java) -> FollowerViewModel() as T

            else -> throw IllegalArgumentException("unknown ViewModel class : ${modelClass.name}")
        }
    }
}