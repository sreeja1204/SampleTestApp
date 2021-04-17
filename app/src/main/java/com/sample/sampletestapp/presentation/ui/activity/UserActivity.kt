package com.sample.sampletestapp.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.sample.sampletestapp.R
import com.sample.sampletestapp.di.component.APIComponent
import com.sample.sampletestapp.di.component.DaggerAPIComponent
import com.sample.sampletestapp.di.module.APIModule
import com.sample.sampletestapp.network.model.User
import com.sample.sampletestapp.presentation.ui.adapter.UserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.collections.ArrayList

class UserActivity() : AppCompatActivity(), Callback<List<User>>, SwipeRefreshLayout.OnRefreshListener,
    Parcelable {

    @BindView(R.id.user_list_help_text)
    internal var helpTextView: TextView? = null
    @BindView(R.id.user_list_recycler_view)
    internal var userRecyclerView: RecyclerView? = null
    @BindView(R.id.user_list_swipe_refresh_layout)
    internal var swipeRefreshLayout: SwipeRefreshLayout? = null

    private var userAdapter: UserAdapter? = null
    private var APIComponent: APIComponent? = null
    private var userList: List<User>? = null

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
        this.setContentView(R.layout.activity_user)
        ButterKnife.bind(this)
        userList = arrayListOf()
        userAdapter = UserAdapter()
        userRecyclerView?.setLayoutManager(LinearLayoutManager(this))
        userRecyclerView?.setAdapter(userAdapter)

        swipeRefreshLayout?.setOnRefreshListener(this)

        APIComponent = DaggerAPIComponent.builder().aPIModule(APIModule()).build()

        getData();

        //return view
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    private fun getData() {
        swipeRefreshLayout?.setRefreshing(true)
        val userList = APIComponent?.userAPI?.users
        userList?.enqueue(this)
    }


    private fun refreshViews() {
        swipeRefreshLayout?.setRefreshing(false)
        userList?.let { userAdapter?.updateList(it) }
        helpTextView?.setVisibility(if (userList?.size!! > 0) View.GONE else View.VISIBLE)
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
