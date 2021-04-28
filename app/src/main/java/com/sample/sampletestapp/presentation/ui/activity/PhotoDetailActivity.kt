package com.sample.sampletestapp.presentation.ui.activity


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.sampletestapp.BR
import com.sample.sampletestapp.R
import com.sample.sampletestapp.databinding.ActivityPhotoDetail2Binding
import com.sample.sampletestapp.network.model.Photo
import com.sample.sampletestapp.presentation.ui.viewmodel.PhotoDetailActivityViewModel
import kotlinx.android.synthetic.main.activity_photo_detail2.*

class PhotoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val albumid = intent.getIntExtra("albumId",0)
        val photoId = intent.getIntExtra("photoId",0)

        val viewModel = makeAPICall(albumid,photoId)
        setupBinding(viewModel)
    }

    fun makeAPICall(albumId:Int, photoId:Int) : PhotoDetailActivityViewModel {
        val viewModel =  ViewModelProviders.of(this).get(PhotoDetailActivityViewModel::class.java)
        viewModel.getPhotoDetailListDataObserver().observe(this, Observer<List<Photo>>{
            progressbar.visibility = View.GONE
            if(it != null) {
                //update the adapter
                viewModel.setDetailAdapterData(it)
            } else {
                Toast.makeText(this@PhotoDetailActivity, "Error in fetching data", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.makeApiCallPhotoDetail(albumId,photoId)

        return viewModel
    }

    fun setupBinding(viewModel: PhotoDetailActivityViewModel){

        val activityPhotoDetail2Binding: ActivityPhotoDetail2Binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_detail2)
        activityPhotoDetail2Binding.setVariable(BR.photoDetailViewModel, viewModel)
        activityPhotoDetail2Binding.executePendingBindings()
        photo_detail_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@PhotoDetailActivity)
        }
    }
}




