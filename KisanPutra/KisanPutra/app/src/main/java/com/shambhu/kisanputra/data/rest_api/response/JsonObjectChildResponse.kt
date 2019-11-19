package com.shambhu.kisanputra.data.rest_api.response

import com.google.gson.annotations.SerializedName

class JsonObjectChildResponse<T> {

    @SerializedName("count")
    var count : String =""

    @SerializedName("items")
    var items : T? = null



}