package com.sample.sampletestapp.di.module

import com.sample.sampletestapp.network.api.PhotoAPI
import com.sample.sampletestapp.network.api.UsersAPI
import com.sample.sampletestapp.network.utils.APIConstants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class APIModule {

    val gsonConverterFactory: GsonConverterFactory
        @Provides
        get() = GsonConverterFactory.create()

    @Provides
    fun getPhotoAPI(retrofit: Retrofit): PhotoAPI {
        return retrofit.create(PhotoAPI::class.java)
    }

    @Provides
    fun getUserAPI(retrofit: Retrofit): UsersAPI {
        return retrofit.create(UsersAPI::class.java)
    }

    @Provides
    fun getRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(APIConstants.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
}
