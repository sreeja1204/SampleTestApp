package com.sample.sampletestapp.presentation.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sample.sampletestapp.R
import com.sample.sampletestapp.di.component.APIComponent
import com.sample.sampletestapp.di.component.DaggerAPIComponent
import com.sample.sampletestapp.di.module.APIModule
import com.sample.sampletestapp.network.model.Photo
import com.sample.sampletestapp.presentation.ui.adapter.PhotoAdapter
import kotlinx.android.synthetic.main.view_photo_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.net.URL
import java.util.ArrayList

class PhotoActivity : AppCompatActivity(), Callback<List<Photo>>,
    SwipeRefreshLayout.OnRefreshListener {

//    @BindView(R.id.photo_list_help_text)
//    internal var helpTextView: TextView? = null
//    @BindView(R.id.photo_list_recycler_view)
//    internal var photosRecyclerView: RecyclerView? = null
//    @BindView(R.id.photo_list_swipe_refresh_layout)
//    internal var swipeRefreshLayout: SwipeRefreshLayout? = null

    private lateinit var helpTextView: TextView
    private lateinit var photosRecyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


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
        helpTextView = findViewById(R.id.photo_list_help_text)
        photosRecyclerView = findViewById(R.id.photo_list_recycler_view)
        swipeRefreshLayout = findViewById(R.id.photo_list_swipe_refresh_layout)
        photoList = arrayListOf()
        photoAdapter = PhotoAdapter()
        photosRecyclerView?.setLayoutManager(LinearLayoutManager(this))
        photosRecyclerView?.setAdapter(photoAdapter)

        swipeRefreshLayout?.setOnRefreshListener(this)

        APIComponent = DaggerAPIComponent.builder().aPIModule(APIModule()).build()
        getData()
    }

    private fun getData() {
        swipeRefreshLayout?.setRefreshing(true)
        val photoList = APIComponent?.photoAPI?.photos
        photoList?.enqueue(this)
    }

    private fun refreshViews() {
        swipeRefreshLayout?.setRefreshing(false)
        photoList?.let { photoAdapter?.updateList(it) }
        helpTextView?.setVisibility(if (photoList?.size!! > 0) View.GONE else View.VISIBLE)
    }


    inner class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoHolder>()  {

        private var photoList: List<Photo>? = null

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
            return 10
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_photo_list_item, parent, false)


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
        inner class PhotoHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
            //2
            private var view: View = v
            private var photo: Photo? = null



            fun check(photo : Photo){
                view.photo_list_item_title.text = photo.title
                DownloadImageForThumbnail(view.photo_list_item_image).execute(photo.thumbnailUrl)

            }
            //3
            init {
                v.setOnClickListener(this)
            }

            //4
            override fun onClick(v: View) {
                Log.d("RecyclerView", "CLICK!")
            }

//            companion object {
//                //5
//                private val PHOTO_KEY = "PHOTO"
//            }
        }


        private inner class DownloadImageForThumbnail(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
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
//                    val `in` = java.net.URL(imageURL).openStream()
//                    image = BitmapFactory.decodeStream(`in`)
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

}
