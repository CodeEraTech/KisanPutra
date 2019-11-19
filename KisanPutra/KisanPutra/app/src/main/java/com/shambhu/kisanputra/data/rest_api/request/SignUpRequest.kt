package com.shambhu.kisanputra.data.rest_api.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SignUpRequest (

    @SerializedName("name")
    var name: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("mobile_no")
    var mobile_no: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("c_password")
    var c_password: String

    ) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(mobile_no)
        parcel.writeString(password)
        parcel.writeString(c_password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SignUpRequest> {
        override fun createFromParcel(parcel: Parcel): SignUpRequest {
            return SignUpRequest(parcel)
        }

        override fun newArray(size: Int): Array<SignUpRequest?> {
            return arrayOfNulls(size)
        }
    }

}
