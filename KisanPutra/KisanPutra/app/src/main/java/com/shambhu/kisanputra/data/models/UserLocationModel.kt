package com.shambhu.kisanputra.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.shambhu.kisanputra.base.BaseModel

data class UserLocationModel (
    var country: String ="",
    var state: String="",
    var lat : Double=0.0,
    var long : Double=0.0
){
    var country_code : String = ""
    var state_code : String = ""
}