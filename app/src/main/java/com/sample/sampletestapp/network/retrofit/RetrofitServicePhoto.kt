package com.sample.sampletestapp.network.retrofit

import com.sample.sampletestapp.network.model.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServicePhoto {
    @GET("photos")
    fun getPhotoDataFromAPI(): Call<List<Photo>>

    @GET("photos")
    fun getPhotoDetailDataFromAPI(@Query("albumId")albumId : Int,
                                  @Query("id")id:Int) : Call<List<Photo>>

}