package com.konstantinfo.struct.repositories

import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.shambhu.kisanputra.data.models.UserData
import com.shambhu.kisanputra.data.models.response.HomeProductResponse
import com.shambhu.kisanputra.data.models.response.getCart_list_model
import com.shambhu.kisanputra.data.rest_api.apis.AppApiService
import com.shambhu.kisanputra.data.rest_api.request.LoginRequest
import com.shambhu.kisanputra.data.rest_api.request.SignUpRequest
import com.shambhu.kisanputra.data.rest_api.response.BaseResponse
import com.shambhu.kisanputra.data.rest_api.response.JsonObjectResponse
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class CartRepository(private val mAppApiService : AppApiService) {


    fun getProductlists(token: String): Observable<JsonObjectResponse<getCart_list_model>> {
        return mAppApiService.cartlist(token)
    }


}