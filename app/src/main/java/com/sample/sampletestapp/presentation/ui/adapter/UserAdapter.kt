package com.sample.sampletestapp.presentation.ui.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.sample.sampletestapp.R
import com.sample.sampletestapp.network.model.User
import com.sample.sampletestapp.presentation.ui.activity.PhotoActivity
import kotlinx.android.synthetic.main.view_user_list_item.view.*

class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    private var userList: List<User>? = null

    @BindView(R.id.user_list_item_ID)
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
        userList?.get(position)?.let { holder.check(it) }
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

        fun check(user : User){
            view.user_list_item_ID.text = user.id.toString()
            view.user_list_item_email.text = user.email
            view.user_list_item_phone.text = user.phone
            view.user_list_item_name.text = user.name
        }

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }
    }


}