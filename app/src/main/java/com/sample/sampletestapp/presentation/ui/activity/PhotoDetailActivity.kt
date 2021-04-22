package com.sample.sampletestapp.presentation.ui.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.sampletestapp.R
import com.sample.sampletestapp.presentation.ui.adapter.PhotoDetailAdapter

class PhotoDetailActivity : AppCompatActivity() {

    private lateinit var photodetailRecyclerView: RecyclerView
    private var photodetailAdapter: PhotoDetailAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail2)
        photodetailRecyclerView = findViewById(R.id.photo_detail_list_recycler_view)

        val intent = intent
        val url = intent.getStringExtra("url")
        val name = intent.getStringExtra("title")
        val albumid = intent.getIntExtra("albumId",0)
        val photoId = intent.getIntExtra("photoId",0)

        photodetailAdapter = PhotoDetailAdapter(url, name, albumid,photoId)
        photodetailRecyclerView?.setLayoutManager(LinearLayoutManager(this))
        photodetailRecyclerView?.setAdapter(photodetailAdapter)
    }
}




