package com.shambhu.kisanputra.data.rest_api.request

import com.google.gson.annotations.SerializedName

data class LoginRequest (

    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("device_token")
    var deviceToken: String,

    @SerializedName("device_type")
    var deviceType: String) {

}
