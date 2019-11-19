package com.shambhu.kisanputra.data.rest_api.request

import com.google.gson.annotations.SerializedName

data class PasswordRequest(
    @SerializedName("email")
    var email: String) {

}
