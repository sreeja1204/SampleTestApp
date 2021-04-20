package com.sample.sampletestapp.presentation.ui.adapter

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.provider.Settings.Global.getString
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
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sample.sampletestapp.network.model.Photo
import kotlinx.android.synthetic.main.view_photo_detail_list_item.view.*
import kotlinx.android.synthetic.main.view_photo_list_item.view.*
import android.graphics.Bitmap
import java.net.URL
import java.net.URLConnection


class PhotoDetailAdapter(val url: String, val title: String, val size: Int, val albumId: Int, val position: Int) : RecyclerView.Adapter<PhotoDetailAdapter.PhotoDetailHolder>()  {

    //private var photoList: List<Photo>? = null

    @BindView(R.id.photo_detail_list_item_image)
    var imageView: ImageView? = null
    @BindView(R.id.photo_detail_list_item_title)
    var textView: TextView? = null

    private fun bindData(photo: Photo) {
        textView?.text ?: photo.title;
    }

    override fun onBindViewHolder(holder: PhotoDetailHolder, position: Int) {
        position.let { holder.check(title, url, albumId) }
        //imageView?.let { DownloadImageFromInternet(it).execute(url) }
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
        //imageView =
                // DownloadImageFromInternet(view.photo_list_item_image).execute("https://i.pinimg.com/236x/f0/7f/5d/f07f5d1eba874f5170f6d300aaf50168.jpg")

            return PhotoDetailHolder(view)    }
//
//    fun updateList(photoList: List<Photo>) {
//        this.photoList = photoList
//        this.notifyDataSetChanged()
//    }


//    private fun loadImage(): Bitmap {
//        val loading = ImageLoader(context)
//        val request =  ImageRequest.Builder(context).data("https://i.pinimg.com/236x/f0/7f/5d/f07f5d1eba874f5170f6d300aaf50168.jpg").build()
//        val result = (loading.execute(request) as SuccessResult).drawable
//        return (result as BitmapDrawable).bitmap
//    }

    //1
   inner class PhotoDetailHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var photo: Photo? = null



        fun check(title: String, url: String, albumId: Int){
            view.photo_detail_list_item_albumid.text = albumId.toString()
            view.photo_detail_list_item_title.text = title
            DownloadImageForFullImage(view.photo_detail_list_item_image).execute(url)

        }
        //3
        init {
            v.setOnClickListener(this)
        }

        //4
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }
//
//        companion object {
//            //5
//            private val PHOTO_KEY = "PHOTO"
//        }
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
//                val `in` = URL(imageURL).openStream()
//                image = BitmapFactory.decodeStream(`in`)
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



