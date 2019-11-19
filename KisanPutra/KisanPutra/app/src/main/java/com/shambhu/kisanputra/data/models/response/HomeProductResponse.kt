package com.shambhu.kisanputra.data.models.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.shambhu.kisanputra.data.rest_api.response.BaseResponse

data class HomeProductResponse(
    @SerializedName("slider")
    @Expose
    var slider : List<Slider>,

    @SerializedName("product")
    @Expose
    var  product : List<Product>,

    @SerializedName("categories")
    @Expose
    var categories : List<Category>,

    @SerializedName("others")
    @Expose
    var others : Others
) : BaseResponse() {

    inner class Slider(
        @SerializedName("image")
        @Expose
        var image : String,
    @SerializedName("link")
    @Expose
    var link : String,
        @SerializedName("description")
    @Expose
    var description : String
    ){
    }

    inner class Product(
    @SerializedName("id")
    @Expose
    var id :Integer ,
    @SerializedName("name")
    @Expose
    var name :String ,
    @SerializedName("catid")
    @Expose
    var catid :String ,
    @SerializedName("qty")
    @Expose
    var qty :String ,
    @SerializedName("messurment")
    @Expose
    var messurment :String ,
    @SerializedName("price")
    @Expose
    var price :Integer ,
    @SerializedName("image")
    @Expose
    var image : List<Image>,
    @SerializedName("type")
    @Expose
    var type :String ,
    @SerializedName("featured_image")
    @Expose
    var featuredImage :String ,
    @SerializedName("description")
    @Expose
    var description :String ,
    @SerializedName("subscription_histroy")
    @Expose
    var subscriptionHistroy :Boolean ,
    @SerializedName("time")
    @Expose
    var time :Time
    ){
        inner class Image(
            @SerializedName("url")
            @Expose
            var url : String
        ){}

        inner class Time(
            @SerializedName("start_time")
            @Expose
            var startTime : String,
            @SerializedName("end_time")
            @Expose
            var endTime : String
        ){}
    }

    inner  class  Category(
    @SerializedName("id")
    @Expose
    var id : Integer,
    @SerializedName("name")
    @Expose
    var name : String,
    @SerializedName("type")
    @Expose
    var type : String,
    @SerializedName("image")
    @Expose
    var image : String,
    @SerializedName("description")
    @Expose
    var description : String
    ){}

    inner class Others(
        @SerializedName("essential")
        @Expose
        var  essential: Essential
    ){

        inner class Essential(
        @SerializedName("name")
        @Expose
        var name: String,
        @SerializedName("data")
        @Expose
        var data : List<EssentialData>
        ){

            inner class EssentialData(
            @SerializedName("id")
            @Expose
            var id : Integer ,
            @SerializedName("name")
            @Expose
            var name : String ,
            @SerializedName("catid")
            @Expose
            var catid : String ,
            @SerializedName("qty")
            @Expose
            var qty : String ,
            @SerializedName("messurment")
            @Expose
            var messurment : String ,
            @SerializedName("price")
            @Expose
            var price : Integer ,
            @SerializedName("image")
            @Expose
            var image : List<Image>,
            @SerializedName("type")
            @Expose
            var type : String ,
            @SerializedName("featured_image")
            @Expose
            var featuredImage : String ,
            @SerializedName("description")
            @Expose
            var description : String ,
            @SerializedName("subscription_histroy")
            @Expose
            var subscriptionHistroy : Boolean ,
            @SerializedName("time")
            @Expose
            var time : Time
            ){
                inner class Image(
                    @SerializedName("url")
                    @Expose
                    var url : String
                ){}

                inner class Time(
                    @SerializedName("start_time")
                    @Expose
                    var startTime : String,
                    @SerializedName("end_time")
                    @Expose
                    var endTime : String
                ){}
            }
        }
    }
}