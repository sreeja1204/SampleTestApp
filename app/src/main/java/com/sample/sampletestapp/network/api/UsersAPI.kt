package com.sample.sampletestapp.network.api

import com.sample.sampletestapp.network.model.User
import retrofit2.Call
import retrofit2.http.GET

interface UsersAPI {
    @get:GET("users")
    val users: Call<List<User>>
}