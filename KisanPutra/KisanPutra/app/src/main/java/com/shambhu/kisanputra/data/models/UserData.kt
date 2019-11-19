package com.shambhu.kisanputra.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.shambhu.kisanputra.base.BaseModel

data class UserData (
    @SerializedName("id")
    @Expose
    val id : Int,
    @SerializedName("name")
    @Expose
    val name : String,
    @SerializedName("email")
    @Expose
    val email : String,
    @SerializedName("mobile_no")
    @Expose
    val mobileNo : String,
    @SerializedName("access_token")
    @Expose
    val access_token : String,
    @SerializedName("account_type")
    @Expose
    val account_type : String,
    @SerializedName("device_id")
    @Expose
    val device_id : String,
    @SerializedName("token")
    @Expose
    val token : String
): BaseModel(){}