package com.sample.sampletestapp.network.utils

import com.sample.sampletestapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(APIConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(logInterceptorClient)
            .build()

    //Enable API logs in debug mode
    val logInterceptorClient: OkHttpClient
        get() {
            val clientBuilder = OkHttpClient.Builder()

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BASIC
                clientBuilder.addInterceptor(interceptor)
            }

            return clientBuilder.build()
        }
}
