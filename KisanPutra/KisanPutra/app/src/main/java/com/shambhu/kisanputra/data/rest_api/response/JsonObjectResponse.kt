package com.shambhu.kisanputra.data.rest_api.response

import com.google.gson.annotations.SerializedName

open class JsonObjectResponse<T> : BaseResponse(){

    @SerializedName("data")
    var dataObject : T? = null

}