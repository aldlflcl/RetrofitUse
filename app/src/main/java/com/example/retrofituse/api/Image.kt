package com.example.retrofituse.api

import com.squareup.moshi.Json

data class Image(
    val id: String,
    val description: String,
    val urls: Urls,
    @UserName
    @Json(name = "user")
    val userName: String
)