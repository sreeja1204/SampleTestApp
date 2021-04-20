package com.sample.sampletestapp.presentation.ui.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
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
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sample.sampletestapp.R
import com.sample.sampletestapp.network.model.Photo
import com.sample.sampletestapp.presentation.ui.activity.PhotoActivity
import com.sample.sampletestapp.presentation.ui.activity.PhotoDetailActivity
import kotlinx.android.synthetic.main.view_photo_list_item.view.*

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoHolder>()  {

    private var photoList: List<Photo>? = null

    @BindView(R.id.photo_list_item_image)
    var imageView: ImageView? = null
    @BindView(R.id.photo_list_item_title)
    var textView: TextView? = null

    private fun bindData(photo: Photo) {
        textView?.text ?: photo.title;
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        photoList?.get(position)?.let { holder.check(it) }
        //imageView?.let { DownloadImageFromInternet(it).execute(photoList?.get(position)?.url) }
        holder.itemView.setOnClickListener { view ->
            val activity = holder.itemView.context as Activity
            val intent = Intent(activity, PhotoDetailActivity::class.java)
            intent.putExtra("url",photoList?.get(position)?.url)
            intent.putExtra("title",photoList?.get(position)?.title)
            intent.putExtra("size", photoList?.size)
            intent.putExtra("albumId", photoList?.get(position)?.albumId)
            intent.putExtra("position",position)
            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return photoList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_photo_list_item, parent, false)
        //imageView =
       // DownloadImageFromInternet(view.photo_list_item_image).execute("https://i.pinimg.com/236x/f0/7f/5d/f07f5d1eba874f5170f6d300aaf50168.jpg")

        return PhotoHolder(view)    }

    fun updateList(photoList: List<Photo>) {
        this.photoList = photoList
        this.notifyDataSetChanged()
    }


//    private fun loadImage(): Bitmap {
//        val loading = ImageLoader(context)
//        val request =  ImageRequest.Builder(context).data("https://i.pinimg.com/236x/f0/7f/5d/f07f5d1eba874f5170f6d300aaf50168.jpg").build()
//        val result = (loading.execute(request) as SuccessResult).drawable
//        return (result as BitmapDrawable).bitmap
//    }

    //1
    class PhotoHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var photo: Photo? = null



        fun check(photo : Photo){
            view.photo_list_item_title.text = photo.title

        }
        //3
        init {
            v.setOnClickListener(this)
        }

        //4
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        companion object {
            //5
            private val PHOTO_KEY = "PHOTO"
        }
    }


    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        init {
            Toast.makeText(imageView.context, "Please wait, it may take a few minute...",     Toast.LENGTH_SHORT).show()
        }
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
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



