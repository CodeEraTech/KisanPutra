package com.konstantinfo.struct.repositories

import com.shambhu.kisanputra.data.models.UserData
import com.shambhu.kisanputra.data.rest_api.apis.AppApiService
import com.shambhu.kisanputra.data.rest_api.request.LoginRequest
import com.shambhu.kisanputra.data.rest_api.request.SignUpRequest
import com.shambhu.kisanputra.data.rest_api.response.BaseResponse
import com.shambhu.kisanputra.data.rest_api.response.JsonObjectResponse
import com.shambhu.social.UserModel
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class UserDetailsRepository(private val mAppApiService : AppApiService) {

    fun check_user_id_email(email: String): Observable<BaseResponse> {
        return mAppApiService.check_user_id_email(email)
    }

    fun check_user_id_phone(mobile_no: String): Observable<BaseResponse> {
        return mAppApiService.check_user_id_phone(mobile_no)
    }

    fun check_user_id_both(email: String,mobile_no: String): Observable<BaseResponse> {
        return mAppApiService.check_user_id_both(email,mobile_no)
    }

    fun registerNewUser(request: SignUpRequest): Observable<JsonObjectResponse<UserData>> {
        return mAppApiService.registerNewUser(request)
    }


    fun registerUser_withEmail(userModel: UserModel,type: String): Observable<JsonObjectResponse<UserData>> {
        return mAppApiService.sociallogin_email(type,userModel.name,userModel.email)
    }


    fun registerUser_withMobile(userModel: UserModel,type: String): Observable<JsonObjectResponse<UserData>> {
        return mAppApiService.sociallogin_mobile(type,userModel.name,userModel.mobile_no)
    }

    fun registerUser_withMobileNoname(userModel: UserModel,type: String): Observable<JsonObjectResponse<UserData>> {
        return mAppApiService.sociallogin_mobileNoname(type,userModel.mobile_no)
    }

    fun registerUser_withBoth(userModel: UserModel,type: String): Observable<JsonObjectResponse<UserData>> {
        return mAppApiService.sociallogin_both(type,userModel.name,userModel.mobile_no,userModel.email)
    }

}