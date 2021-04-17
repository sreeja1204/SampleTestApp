package com.sample.sampletestapp.presentation.ui.adapter

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
import com.sample.sampletestapp.R
import com.sample.sampletestapp.network.model.Photo

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoHolder>()  {

    private var photoList: List<Photo>? = null

    @BindView(R.id.photo_list_item_image)
    var imageView: ImageView? = null
    @BindView(R.id.photo_list_item_title)
    var textView: TextView? = null

    private fun bindData(photo: Photo) {
        //            RequestOptions options = new RequestOptions()
        //                    .centerCrop()
        //                    .placeholder(R.mipmap.ic_launcher_round)
        //                    .error(R.mipmap.ic_launcher_round);

        //Glide.with(context).load(photo.getThumbnailUrl()).apply(options).into(imageView);

        //textView.setText(photo.getTitle())
        textView?.text ?: photo.title;
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        photoList?.get(position)?.let { bindData(it) }
        holder.itemView.setOnClickListener { view ->
            Toast.makeText(
                view.context,
                "Clicked Recycler View + BindViewHolder Photos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return photoList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_photo_list_item, parent, false)
        return PhotoHolder(view)    }

    fun updateList(photoList: List<Photo>) {
        this.photoList = photoList
        notifyDataSetChanged()
    }


    //1
    class PhotoHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var photo: Photo? = null

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


}



