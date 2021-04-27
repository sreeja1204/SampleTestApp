package com.sample.sampletestapp.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.ButterKnife
import com.sample.sampletestapp.BR
import com.sample.sampletestapp.R
import com.sample.sampletestapp.databinding.ActivityUserBinding
import com.sample.sampletestapp.di.component.APIComponent
import com.sample.sampletestapp.di.component.DaggerAPIComponent
import com.sample.sampletestapp.di.module.APIModule
import com.sample.sampletestapp.network.model.User
import com.sample.sampletestapp.presentation.ui.adapter.UserAdapter
import com.sample.sampletestapp.presentation.ui.viewmodel.UserActivityViewModel
import kotlinx.android.synthetic.main.activity_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class UserActivity() : AppCompatActivity(), Callback<List<User>>, SwipeRefreshLayout.OnRefreshListener,
    Parcelable {

    private var userAdapter: UserAdapter? = null
    private var APIComponent: APIComponent? = null
    private var userList: List<User>? = null

    //private lateinit var helpTextView: TextView
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onRefresh() {
        getData()    }

    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
        if (response.isSuccessful && response.body() != null) {
            userList = response.body()
        }
        refreshViews()    }

    override fun onFailure(call: Call<List<User>>, t: Throwable) {
        refreshViews()
        Timber.e(t)    }


    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this.setContentView(R.layout.activity_user)
        val viewModel = makeAPICall()
        setupBinding(viewModel)
        //ButterKnife.bind(this)
        //helpTextView = findViewById(R.id.user_list_help_text)
        //userRecyclerView = findViewById(R.id.user_list_recycler_view)
        swipeRefreshLayout = findViewById(R.id.user_list_swipe_refresh_layout)
        //userList = arrayListOf()
        //userAdapter = UserAdapter()
        //userRecyclerView?.setLayoutManager(LinearLayoutManager(this))
        //userRecyclerView?.setAdapter(userAdapter)

       // APIComponent = DaggerAPIComponent.builder().aPIModule(APIModule()).build()
        //getData();
        swipeRefreshLayout?.setOnRefreshListener(this)
    }

    fun setupBinding(viewModel: UserActivityViewModel){

        val activityUserBinding: ActivityUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_user)
        activityUserBinding.setVariable(BR.userViewModel, viewModel)
        activityUserBinding.executePendingBindings()
        user_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@UserActivity)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    private fun getData() {
        swipeRefreshLayout?.setRefreshing(true)
//        val userList = APIComponent?.userAPI?.users
//        userList?.enqueue(this)
    }


    private fun refreshViews() {
        swipeRefreshLayout?.setRefreshing(false)
        //userList?.let { userAdapter?.updateList(it) }

        //helpTextView?.setVisibility(if (userList?.size!! > 0) View.GONE else View.VISIBLE)
    }

   fun makeAPICall() : UserActivityViewModel{
       val viewModel =  ViewModelProviders.of(this).get(UserActivityViewModel::class.java)
       viewModel.getUserListDataObserver().observe(this, Observer<List<User>>{
           //progressbar.visibility = View.GONE
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

    companion object CREATOR : Parcelable.Creator<UserActivity> {
        override fun createFromParcel(parcel: Parcel): UserActivity {
            return UserActivity(parcel)
        }

        override fun newArray(size: Int): Array<UserActivity?> {
            return arrayOfNulls(size)
        }
    }
}
