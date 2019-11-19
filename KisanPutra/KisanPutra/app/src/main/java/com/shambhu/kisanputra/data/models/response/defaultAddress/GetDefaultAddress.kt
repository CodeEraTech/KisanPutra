package com.shambhu.kisanputra.data.models.response.defaultAddress

import com.google.gson.annotations.SerializedName

class GetDefaultAddress {

    @SerializedName("data")
    var data: List<Datum>? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("status")
    var status: Boolean? = false
    class Datum {

        @SerializedName("address")
        var address: String? = null
        @SerializedName("city")
        var city: String? = null
        @SerializedName("country")
        var country: String? = null
        @SerializedName("created_at")
        var createdAt: String? = null
        @SerializedName("email")
        var email: String? = null
        @SerializedName("id")
        var id: Long? = null
        @SerializedName("landmark")
        var landmark: String? = null
        @SerializedName("phone")
        var phone: String? = null
        @SerializedName("pincode")
        var pincode: Long? = null
        @SerializedName("starte")
        var starte: String? = null
        @SerializedName("type")
        var type: String? = null
        @SerializedName("updated_at")
        var updatedAt: String? = null
        @SerializedName("user_id")
        var userId: Long? = null




    }
}
