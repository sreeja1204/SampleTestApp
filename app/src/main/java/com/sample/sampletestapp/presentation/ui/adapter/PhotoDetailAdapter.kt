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
import java.net.URL


class PhotoDetailAdapter(val url: String, val title: String, val size: Int, val albumId: Int, val position: Int, val photoId: Int) : RecyclerView.Adapter<PhotoDetailAdapter.PhotoDetailHolder>()  {


    @BindView(R.id.photo_detail_list_item_image)
    var imageView: ImageView? = null
    @BindView(R.id.photo_detail_list_item_title)
    var textView: TextView? = null


    override fun onBindViewHolder(holder: PhotoDetailHolder, position: Int) {
        position.let { holder.bindData(title, url, albumId, photoId) }
        holder.itemView.setOnClickListener { view ->
            Toast.makeText(
                view.context,
                "Clicked Recycler View + BindViewHolder PhotoDetail",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoDetailHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_photo_detail_list_item, parent, false)

            return PhotoDetailHolder(view)    }

   inner class PhotoDetailHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v

        fun bindData(title: String, url: String, albumId: Int, photoId: Int){
            view.photo_detail_list_item_photoid.text = "Photo ID :"+photoId.toString()
            view.photo_detail_list_item_albumid.text = "Album ID :"+albumId.toString()
            view.photo_detail_list_item_title.text = title
            DownloadImageForFullImage(view.photo_detail_list_item_image).execute(url)

        }
        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }
    }


    private inner class DownloadImageForFullImage(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
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



