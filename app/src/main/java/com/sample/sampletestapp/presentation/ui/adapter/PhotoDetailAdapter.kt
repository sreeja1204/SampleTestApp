package com.sample.sampletestapp.presentation.ui.adapter

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sample.sampletestapp.R
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import kotlinx.android.synthetic.main.view_photo_detail_list_item.view.*
import android.graphics.Bitmap
import androidx.databinding.BindingAdapter
import com.sample.sampletestapp.databinding.ActivityPhotoDetail2Binding
import com.sample.sampletestapp.databinding.ViewPhotoDetailListItemBinding
import com.sample.sampletestapp.databinding.ViewPhotoListItemBinding
import com.sample.sampletestapp.network.model.Photo
import java.net.URL


class PhotoDetailAdapter() : RecyclerView.Adapter<PhotoDetailAdapter.PhotoDetailHolder>()  {

    val size: Int = 1
    private var photoList: List<Photo>? = null

    override fun onBindViewHolder(holder: PhotoDetailHolder, position: Int) {
        photoList?.get(position)?.let { holder.bindData(it) }
    }

    override fun getItemCount(): Int {
        return size
    }

    fun updateList(photoList: List<Photo>) {
        this.photoList = photoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoDetailHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewPhotoDetailListItemBinding.inflate(layoutInflater)
        return PhotoDetailHolder(binding)
           }

   inner class PhotoDetailHolder(val binding: ViewPhotoDetailListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(photo:Photo){
            binding.photo = photo
            binding.executePendingBindings()
       }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(thubmImage: ImageView, url: String?) {
            DownloadImageForFullImage(thubmImage).execute(url)
        }

    }


    private class DownloadImageForFullImage(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        init {
            Toast.makeText(imageView.context, "Please wait, it may take a few minute...",     Toast.LENGTH_SHORT).show()
        }
        override fun doInBackground(vararg urls: String?): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val urlConnection = URL(imageURL).openConnection()
                urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0")
                val `is` = urlConnection.getInputStream()
                image = BitmapFactory.decodeStream(`is`)
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}



