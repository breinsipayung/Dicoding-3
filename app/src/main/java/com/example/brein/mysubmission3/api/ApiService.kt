package com.example.brein.mysubmission3.api

import com.example.brein.mysubmission3.model.DetailUserResponse
import com.example.brein.mysubmission3.model.User
import com.example.brein.mysubmission3.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: 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")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: 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")
    fun getFollower(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}