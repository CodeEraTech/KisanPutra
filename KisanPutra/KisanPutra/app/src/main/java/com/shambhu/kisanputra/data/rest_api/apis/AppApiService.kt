package com.shambhu.kisanputra.data.rest_api.apis

import com.shambhu.kisanputra.data.models.UserData
import com.shambhu.kisanputra.data.models.response.HomeProductResponse
import com.shambhu.kisanputra.data.models.response.getCart_list_model
import com.shambhu.kisanputra.data.rest_api.request.SignUpRequest
import com.shambhu.kisanputra.data.rest_api.response.BaseResponse
import com.shambhu.kisanputra.data.rest_api.response.JsonObjectResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface AppApiService {

    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("api/kpregister")
    fun registerNewUser(@Body signUpReq: SignUpRequest): Observable<JsonObjectResponse<UserData>>

    //@Headers("Content-Type:application/json; charset=UTF-8")
    @FormUrlEncoded
    @POST("api/check_user_id")
    fun check_user_id_email(@Field("email") email: String): Observable<BaseResponse>

    @FormUrlEncoded
    @POST("api/check_user_id")
    fun check_user_id_phone(@Field("mobile_no") mobile_no: String): Observable<BaseResponse>

    @FormUrlEncoded
    @POST("api/check_user_id")
    fun check_user_id_both(@Field("email") email: String,@Field("mobile_no") mobile_no : String): Observable<BaseResponse>


    @FormUrlEncoded
    @POST("api/sociallogin")
    fun sociallogin_email(
        @Field("account_type") account_type: String,
        @Field("name") name : String,
        @Field("email") email : String
        ): Observable<JsonObjectResponse<UserData>>

    @FormUrlEncoded
    @POST("api/sociallogin")
    fun sociallogin_mobile(
        @Field("account_type") account_type: String,
        @Field("name") name : String,
        @Field("mobile_no") mobile_no : String
    ): Observable<JsonObjectResponse<UserData>>

    @FormUrlEncoded
    @POST("api/sociallogin")
    fun sociallogin_mobileNoname(
        @Field("account_type") account_type: String,
        @Field("mobile_no") mobile_no : String
    ): Observable<JsonObjectResponse<UserData>>


    @FormUrlEncoded
    @POST("api/sociallogin")
    fun sociallogin_both(
        @Field("account_type") account_type: String,
        @Field("name") name : String,
        @Field("mobile_no") mobile_no : String,
        @Field("email") email : String
    ): Observable<JsonObjectResponse<UserData>>

   /* @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("/auth/log-in")
    fun loginUser(@Body loginReq: LoginRequest): Observable<JsonObjectResponse<UserData>>*/

   /* @Headers("Content-Type:application/json; charset=UTF-8")
    @PUT("/user/logout")
    fun logoutUser(@Header("Authorization") authToken:String): Observable<BaseResponse>

    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("/user/{userId}")
   fun getUserDetails(@Header("Authorization") authToken:String, @Path("userId") userId : String): Observable<JsonObjectResponse<UserData>>*/

   /* @GET("api/login")
    fun loginUser(): Observable<JsonObjectResponse<UserData>>*/


//    @GET("kisanputra/public/api/fetch_home_products")
//    fun productlists(@Header("Authorization") token : String): Observable<JsonObjectResponse<HomeProductResponse>>

    @GET("api/fetch_home_products")
    fun productlists(@Header("Authorization") token: String): Observable<JsonObjectResponse<HomeProductResponse>>

    @GET("api/cartlist")
    fun cartlist(@Header("Authorization") token: String): Observable<JsonObjectResponse<getCart_list_model>>

}