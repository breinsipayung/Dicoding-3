package com.example.brein.mysubmission3.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val URL = "https://api.github.com/"

    private val retrofit =
        Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}