package com.shambhu.kisanputra.data.rest_api.response

import com.google.gson.annotations.SerializedName

open class JsonArrayResponse<T> : BaseResponse() {

    @SerializedName("data")
     var list : List<T>? = null
}