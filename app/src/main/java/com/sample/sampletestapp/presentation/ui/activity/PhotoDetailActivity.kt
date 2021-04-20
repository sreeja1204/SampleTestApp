package com.sample.sampletestapp.presentation.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.sampletestapp.R
import com.sample.sampletestapp.presentation.ui.adapter.PhotoDetailAdapter

import kotlinx.android.synthetic.main.activity_photo_detail2.*

class PhotoDetailActivity : AppCompatActivity() {

    private lateinit var photodetailRecyclerView: RecyclerView
    private var photodetailAdapter: PhotoDetailAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail2)
        photodetailRecyclerView = findViewById(R.id.photo_detail_list_recycler_view)
        val intent = intent
        val photolist = intent.getStringArrayListExtra("photolist")
        val url = intent.getStringExtra("url")
        val name = intent.getStringExtra("title")

        val size = intent.getIntExtra("size",0)

        val albumid = intent.getIntExtra("albumId",0)

        val position = intent.getIntExtra("position",0)

        photodetailAdapter = PhotoDetailAdapter(url, name, size, albumid, position)
        photodetailRecyclerView?.setLayoutManager(LinearLayoutManager(this))
        photodetailRecyclerView?.setAdapter(photodetailAdapter)
    }
}




