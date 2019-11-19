package com.shambhu.kisanputra.data.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.shambhu.kisanputra.BR

data class DetailAddressModel(
    var individual : Boolean,
    var address : String,
    var streetno : String,
    var buildingName : String,
    var flatno : String
) : BaseObservable() {

   /* var _individual : Boolean
        @Bindable get() = individual
        set(value) {
            individual  = value
           // notifyPropertyChanged(BR.)
        }*/

    var _ind : Boolean
        @Bindable get() = individual
        set(value) {
            individual = value
          //  notifyPropertyChanged(BR._ind)
        }

}