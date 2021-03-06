package com.sample.sampletestapp.presentation.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.sampletestapp.databinding.ViewUserListItemBinding
import com.sample.sampletestapp.network.model.User
import com.sample.sampletestapp.presentation.ui.activity.PhotoActivity

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
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewUserListItemBinding.inflate(layoutInflater)
        return UserHolder(binding)
    }

    fun updateList(userList: List<User>) {
        this.userList = userList
    }

    class UserHolder(val binding: ViewUserListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(user : User){
            binding.user = user
            binding.executePendingBindings()
        }
    }


}