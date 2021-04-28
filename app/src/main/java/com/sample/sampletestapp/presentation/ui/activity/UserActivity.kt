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
import com.sample.sampletestapp.databinding.ActivityUserBinding
import com.sample.sampletestapp.network.model.User
import com.sample.sampletestapp.presentation.ui.viewmodel.UserActivityViewModel
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity() : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = makeAPICall()
        setupBinding(viewModel)
    }

    fun setupBinding(viewModel: UserActivityViewModel){
        val activityUserBinding: ActivityUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_user)
        activityUserBinding.setVariable(BR.userViewModel, viewModel)
        activityUserBinding.executePendingBindings()
        user_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@UserActivity)
        }
    }

   fun makeAPICall() : UserActivityViewModel{
       val viewModel =  ViewModelProviders.of(this).get(UserActivityViewModel::class.java)
       viewModel.getUserListDataObserver().observe(this, Observer<List<User>>{
           if(it != null) {
               //update the adapter
               viewModel.setAdapterData(it)
           } else {
               Toast.makeText(this@UserActivity, "Error in fetching data", Toast.LENGTH_LONG).show()
           }
       })
       viewModel.makeApiCall()

       return viewModel
    }
}
