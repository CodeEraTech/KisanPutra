package com.shambhu.kisanputra.viewmodels

import androidx.lifecycle.MutableLiveData
import com.konstantinfo.struct.repositories.CartRepository
import com.konstantinfo.struct.repositories.HomeRepository
import com.konstantinfo.struct.repositories.UserDetailsRepository
import com.shambhu.kisanputra.base.BaseViewModel
import com.shambhu.kisanputra.data.models.UserData
import com.shambhu.kisanputra.data.models.response.HomeProductResponse
import com.shambhu.kisanputra.data.models.response.getCart_list_model
import com.shambhu.kisanputra.data.rest_api.apis.AppApiService
import com.shambhu.kisanputra.data.rest_api.request.LoginRequest
import com.shambhu.kisanputra.data.rest_api.request.SignUpRequest
import com.shambhu.kisanputra.data.rest_api.response.BaseResponse
import com.shambhu.kisanputra.data.rest_api.response.JsonObjectResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class getcartViewModel @Inject constructor(appApiService: AppApiService) : BaseViewModel() {

    private val cartRepository : CartRepository = CartRepository(appApiService)
     private val cart_list = MutableLiveData<getCart_list_model>()

    fun getcartlist(token: String){
        loading.value = true
        dataLoadError.value = ""


        cartRepository.getProductlists(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                    if(result != null){
                       if (result.status){
                         cart_list.value= result!!.dataObject
                       }else{
                           dataLoadError.value = result.message
                       }
                    }else{
                        dataLoadError.value = "Something went wrong on server"
                    }
                loading.value = false
            }, { error ->
                handleError(error)
                loading.value = false
            })

    }

    fun getCartlistdetail() = cart_list

}