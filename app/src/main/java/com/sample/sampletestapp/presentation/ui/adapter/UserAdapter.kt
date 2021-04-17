package com.sample.sampletestapp.presentation.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.sample.sampletestapp.R
import com.sample.sampletestapp.network.model.User

class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    private var userList: List<User>? = null

    @BindView(R.id.user_list_item_id)
    var textViewID: TextView? = null
    @BindView(R.id.user_list_item_name)
    var textViewName: TextView? = null
    @BindView(R.id.user_list_item_email)
    var textViewEmail: TextView? = null
    @BindView(R.id.user_list_item_phone)
    var textViewPhone: TextView? = null

    private fun bindData(user: User) {
        textViewID?.text = user.id.toString()
        textViewName?.text = user.name
        textViewEmail?.text = user.email
        textViewPhone?.text = user.phone
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        userList?.get(position)?.let { bindData(it) }
        holder.itemView.setOnClickListener { view ->
            Toast.makeText(
                view.context,
                "Clicked Recycler View + BindViewHolder",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_user_list_item, parent, false)
        return UserHolder(view)    }

    fun updateList(userList: List<User>) {
        this.userList = userList
        this.notifyDataSetChanged()
    }

    class UserHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var photo: User? = null


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