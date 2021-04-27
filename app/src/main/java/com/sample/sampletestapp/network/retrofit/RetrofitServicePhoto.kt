package com.sample.sampletestapp.network.retrofit

import com.sample.sampletestapp.network.model.Photo
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServicePhoto {
    @GET("photos")
    fun getPhotoDataFromAPI(): Call<List<Photo>>
}