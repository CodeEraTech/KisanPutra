package com.shambhu.kisanputra.data.rest_api.response

import com.google.gson.annotations.SerializedName

open class JsonItemsArrayResponse<T> : BaseResponse() {

    @SerializedName("data")
    var data : List<T>? = null
}