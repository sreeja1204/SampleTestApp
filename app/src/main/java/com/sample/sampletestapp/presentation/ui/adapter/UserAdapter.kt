package com.sample.sampletestapp.presentation.ui.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.sampletestapp.R
import com.sample.sampletestapp.network.model.User
import com.sample.sampletestapp.presentation.ui.activity.PhotoActivity
import kotlinx.android.synthetic.main.view_user_list_item.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    private var userList: List<User>? = null

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        userList?.get(position)?.let { holder.bindData(it) }
        holder.itemView.setOnClickListener { view ->
            val activity = holder.itemView.context as Activity
            val intent = Intent(activity, PhotoActivity::class.java)
            view.context.startActivity(intent)
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
        private var view: View = v

        fun bindData(user : User){
            view.user_list_item_ID.text = "ID : "+user.id.toString()
            view.user_list_item_email.text = "Email :"+user.email
            view.user_list_item_phone.text = "Phone :"+user.phone
            view.user_list_item_name.text = "Name :"+user.name
        }

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }
    }


}