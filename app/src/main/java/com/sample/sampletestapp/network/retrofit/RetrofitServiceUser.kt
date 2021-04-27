package com.sample.sampletestapp.network.retrofit

import com.sample.sampletestapp.network.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroServiceUser {

    @GET("users")
    fun getUserDataFromAPI(): Call<List<User>>
}