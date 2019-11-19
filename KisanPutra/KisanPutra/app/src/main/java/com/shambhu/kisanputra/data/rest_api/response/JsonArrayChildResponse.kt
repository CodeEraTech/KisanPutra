package com.shambhu.kisanputra.data.rest_api.response

import com.google.gson.annotations.SerializedName

class JsonArrayChildResponse<T> {

    @SerializedName("pages")
    var pages : Int = 0
    @SerializedName("counts")
    var counts : Int = 0
    @SerializedName("items")
    var items : List<T>? = null

}