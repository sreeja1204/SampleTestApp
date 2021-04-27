package com.sample.sampletestapp.presentation.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.sampletestapp.di.component.APIComponent
import com.sample.sampletestapp.di.component.DaggerAPIComponent
import com.sample.sampletestapp.di.module.APIModule
import com.sample.sampletestapp.network.model.User
import com.sample.sampletestapp.network.retrofit.RetroInstance
import com.sample.sampletestapp.network.retrofit.RetroServiceUser
import com.sample.sampletestapp.presentation.ui.adapter.UserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserActivityViewModel : ViewModel(){

    lateinit var userListData: MutableLiveData<List<User>>
    lateinit var userAdapter: UserAdapter
    private var APIComponent: APIComponent


    init{
        userListData = MutableLiveData()
        userAdapter = UserAdapter()
        APIComponent = DaggerAPIComponent.builder().aPIModule(APIModule()).build()

    }

    fun getAdapter() : UserAdapter{
        return userAdapter
    }

    fun setAdapterData(data: List<User>){
        userAdapter.updateList(data)
        userAdapter.notifyDataSetChanged()
    }

    fun getUserListDataObserver(): MutableLiveData<List<User>>{
        return userListData
    }

    fun makeApiCall(){
        val retroInstance = RetroInstance.getRetroInstance().create(RetroServiceUser::class.java)
        val call = retroInstance.getUserDataFromAPI()
        call.enqueue(object : Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                userListData.postValue(null)
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful){
                    userListData.postValue(response.body())
                } else {
                    userListData.postValue(null)
                }
            }
        })
    }
}