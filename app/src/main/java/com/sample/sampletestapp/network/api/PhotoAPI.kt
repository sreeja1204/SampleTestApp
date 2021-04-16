package com.sample.sampletestapp.network.api

import com.sample.sampletestapp.network.model.Photo
import retrofit2.Call
import retrofit2.http.GET

interface PhotoAPI {
    @get:GET("photos")
    val photos: Call<List<Photo>>
}