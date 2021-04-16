package com.sample.sampletestapp.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("name")
    @Expose
    var name: String? = null
        set(title) {
            field = this.name
        }
    @SerializedName("email")
    @Expose
    var email: String? = null
        set(url) {
            field = this.email
        }
    @SerializedName("phone")
    @Expose
    var phone: String? = null

}