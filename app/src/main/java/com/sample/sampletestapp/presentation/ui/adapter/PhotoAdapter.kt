package com.sample.sampletestapp.presentation.ui.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.sample.sampletestapp.R
import com.sample.sampletestapp.databinding.ViewPhotoListItemBinding
import com.sample.sampletestapp.databinding.ViewUserListItemBinding
import com.sample.sampletestapp.network.model.Photo
import com.sample.sampletestapp.presentation.ui.activity.PhotoDetailActivity
import kotlinx.android.synthetic.main.view_photo_list_item.view.*
import java.net.URL

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoHolder>()  {

    private var photoList: List<Photo>? = null

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        photoList?.get(position)?.let { holder.bindData(it) }
        holder.itemView.setOnClickListener { view ->
            val activity = holder.itemView.context as Activity
            val intent = Intent(activity, PhotoDetailActivity::class.java)
            intent.putExtra("albumId", photoList?.get(position)?.albumId)
            intent.putExtra("photoId",photoList?.get(position)?.id)
            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return photoList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewPhotoListItemBinding.inflate(layoutInflater)
        return PhotoHolder(binding)

    }

    fun updateList(photoList: List<Photo>) {
        this.photoList = photoList
        this.notifyDataSetChanged()
    }

    inner class PhotoHolder(val binding: ViewPhotoListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(photo : Photo){
            binding.photo = photo
            binding.executePendingBindings()
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(thubmImage: ImageView, url: String) {
            DownloadImageFromThumbnail(thubmImage).execute(url)
        }

    }

    private class DownloadImageFromThumbnail(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        init {
            Toast.makeText(imageView.context, "Please wait, it may take a few minute...",     Toast.LENGTH_SHORT).show()
        }
        override fun doInBackground(vararg urls: String): Bitmap? {
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



