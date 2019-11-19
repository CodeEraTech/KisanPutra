package com.konstantinfo.struct.repositories

import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.shambhu.kisanputra.data.models.UserData
import com.shambhu.kisanputra.data.models.response.HomeProductResponse
import com.shambhu.kisanputra.data.rest_api.apis.AppApiService
import com.shambhu.kisanputra.data.rest_api.request.LoginRequest
import com.shambhu.kisanputra.data.rest_api.request.SignUpRequest
import com.shambhu.kisanputra.data.rest_api.response.BaseResponse
import com.shambhu.kisanputra.data.rest_api.response.JsonObjectResponse
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class HomeRepository(private val mAppApiService : AppApiService) {


    fun getProductlists(token: String): Observable<JsonObjectResponse<HomeProductResponse>> {
        return mAppApiService.productlists(token)
    }


}