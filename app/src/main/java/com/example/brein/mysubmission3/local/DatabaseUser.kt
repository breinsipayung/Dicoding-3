package com.example.brein.mysubmission3.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [FavoriteUser::class],
    version = 1
)

abstract class DatabaseUser : RoomDatabase() {
    companion object{
        var INSTANCE: DatabaseUser? = null
        fun getDatabase(context: Context) : DatabaseUser?{
            if(INSTANCE == null){
                synchronized(DatabaseUser::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, DatabaseUser::class.java,"database").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favoriteUserDao() : FavoriteUserDao
}