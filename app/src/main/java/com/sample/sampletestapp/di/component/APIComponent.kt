package com.sample.sampletestapp.di.component

import com.sample.sampletestapp.di.module.APIModule
import com.sample.sampletestapp.network.api.PhotoAPI
import com.sample.sampletestapp.network.api.UsersAPI
import dagger.Component

@Component(modules = [APIModule::class])
interface APIComponent {

    val photoAPI: PhotoAPI
    val userAPI: UsersAPI
}