package com.sample.sampletestapp.presentation.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.sampletestapp.network.model.Photo
import com.sample.sampletestapp.network.retrofit.RetroInstance
import com.sample.sampletestapp.network.retrofit.RetrofitServicePhoto
import com.sample.sampletestapp.presentation.ui.adapter.PhotoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoActivityViewModel : ViewModel(){
    lateinit var photoListData: MutableLiveData<List<Photo>>
    lateinit var photoAdapter: PhotoAdapter

    init{
        photoListData = MutableLiveData()
        photoAdapter = PhotoAdapter()
    }

    fun getAdapter() : PhotoAdapter{
        return photoAdapter
    }

    fun setAdapterData(data: List<Photo>){
        photoAdapter.updateList(data)
        photoAdapter.notifyDataSetChanged()
    }


    fun getPhotoListDataObserver(): MutableLiveData<List<Photo>>{
        return photoListData
    }

    fun makeApiCall(){
        val retroInstance = RetroInstance.getRetroInstance().create(RetrofitServicePhoto::class.java)
        val call = retroInstance.getPhotoDataFromAPI()
        call.enqueue(object : Callback<List<Photo>> {
            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                photoListData.postValue(null)
            }

            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                if(response.isSuccessful){
                    photoListData.postValue(response.body())
                } else {
                    photoListData.postValue(null)
                }
            }
        })
    }
}