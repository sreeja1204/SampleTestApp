package com.sample.sampletestapp.presentation.ui.activity


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.sampletestapp.BR
import com.sample.sampletestapp.R
import com.sample.sampletestapp.databinding.ActivityPhotoBinding
import com.sample.sampletestapp.network.model.Photo
import com.sample.sampletestapp.presentation.ui.viewmodel.PhotoActivityViewModel
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = makeAPICall()
        setupBinding(viewModel)
    }

    fun makeAPICall() : PhotoActivityViewModel {
        val viewModel =  ViewModelProviders.of(this).get(PhotoActivityViewModel::class.java)
        viewModel.getPhotoListDataObserver().observe(this, Observer<List<Photo>>{
            if(it != null) {
                //update the adapter
                viewModel.setAdapterData(it)
            } else {
                Toast.makeText(this@PhotoActivity, "Error in fetching data", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.makeApiCall()

        return viewModel
    }

    fun setupBinding(viewModel: PhotoActivityViewModel){

        val activityPhotoBinding: ActivityPhotoBinding = DataBindingUtil.setContentView(this, R.layout.activity_photo)
        activityPhotoBinding.setVariable(BR.photoViewModel, viewModel)
        activityPhotoBinding.executePendingBindings()
        photo_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@PhotoActivity)
        }
    }
}
