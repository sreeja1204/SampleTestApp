package com.sample.sampletestapp.presentation.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.sampletestapp.network.model.Photo
import com.sample.sampletestapp.network.retrofit.RetroInstance
import com.sample.sampletestapp.network.retrofit.RetrofitServicePhoto
import com.sample.sampletestapp.presentation.ui.adapter.PhotoDetailAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoDetailActivityViewModel : ViewModel(){
     var photoDetailListData: MutableLiveData<List<Photo>>
     var photoDetailAdapter: PhotoDetailAdapter

    init{
        photoDetailListData = MutableLiveData()
        photoDetailAdapter = PhotoDetailAdapter()
    }

    fun getDetailAdapter() : PhotoDetailAdapter {
        return photoDetailAdapter
    }

    fun setDetailAdapterData(data: List<Photo>){
        photoDetailAdapter.updateList(data)
        photoDetailAdapter.notifyDataSetChanged()
    }

    fun getPhotoDetailListDataObserver(): MutableLiveData<List<Photo>>{
        return photoDetailListData
    }

    fun makeApiCallPhotoDetail(albumId:Int, photoId:Int){
        val retroInstance = RetroInstance.getRetroInstance().create(RetrofitServicePhoto::class.java)
        val call = retroInstance.getPhotoDetailDataFromAPI(albumId,photoId)
        call.enqueue(object : Callback<List<Photo>> {
            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                photoDetailListData.postValue(null)
            }

            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                if(response.isSuccessful){
                    photoDetailListData.postValue(response.body())
                } else {
                    photoDetailListData.postValue(null)
                }
            }
        })
    }


}