package com.sample.sampletestapp.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.sample.sampletestapp.network.model.Photo
import com.sample.sampletestapp.presentation.ui.adapter.PhotoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.ArrayList

class PhotoActivity : AppCompatActivity(), Callback<List<Photo>>,
    SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.photo_list_help_text)
    internal var helpTextView: TextView? = null
    @BindView(R.id.photo_list_recycler_view)
    internal var photosRecyclerView: RecyclerView? = null
    @BindView(R.id.photo_list_swipe_refresh_layout)
    internal var swipeRefreshLayout: SwipeRefreshLayout? = null

    private var photoAdapter: PhotoAdapter? = null
    private var APIComponent: APIComponent? = null
    private var photoList: List<Photo>? = null


    override fun onRefresh() {
        getData()    }

    override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
        if (response.isSuccessful && response.body() != null) {
            photoList = response.body()
        }
        refreshViews()    }

    override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
        refreshViews()
        Timber.e(t)    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        ButterKnife.bind(this)
        photoList = ArrayList()
        photoAdapter = PhotoAdapter()
        photosRecyclerView?.setLayoutManager(LinearLayoutManager(this))
        photosRecyclerView?.setAdapter(photoAdapter)

        swipeRefreshLayout?.setOnRefreshListener(this)

        APIComponent = DaggerAPIComponent.builder().aPIModule(APIModule()).build()
        getData()
    }

    private fun getData() {
        swipeRefreshLayout?.setRefreshing(true)
        APIComponent?.photoAPI?.photos?.enqueue(this)
    }

    private fun refreshViews() {
        swipeRefreshLayout?.setRefreshing(false)
        photoList?.let { photoAdapter?.updateList(it) }
        helpTextView?.setVisibility(if (photoList?.size!! > 0) View.GONE else View.VISIBLE)
    }
}
