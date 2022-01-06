package com.example.brein.mysubmission3.model

data class DetailUserResponse(
    val login : String,
    val id: Int,
    val avatar_url : String,
    val name: String,
    val followers: String,
    val following: String,
    val public_repos: String,
    val company: String,
    val location: String
)