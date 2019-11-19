package com.shambhu.kisanputra.viewmodels

import androidx.lifecycle.MutableLiveData
import com.konstantinfo.struct.repositories.HomeRepository
import com.konstantinfo.struct.repositories.UserDetailsRepository
import com.shambhu.kisanputra.base.BaseViewModel
import com.shambhu.kisanputra.data.models.UserData
import com.shambhu.kisanputra.data.models.response.HomeProductResponse
import com.shambhu.kisanputra.data.rest_api.apis.AppApiService
import com.shambhu.kisanputra.data.rest_api.request.LoginRequest
import com.shambhu.kisanputra.data.rest_api.request.SignUpRequest
import com.shambhu.kisanputra.data.rest_api.response.BaseResponse
import com.shambhu.kisanputra.data.rest_api.response.JsonObjectResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(appApiService: AppApiService) : BaseViewModel() {

    private val homeDetailRepo : HomeRepository = HomeRepository(appApiService)
     private val productDetails = MutableLiveData<HomeProductResponse>()

    fun getProductlists(token: String){
        loading.value = true
        dataLoadError.value = ""


        homeDetailRepo.getProductlists(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                    if(result != null){
                       if (result.status){
                         productDetails.value= result!!.dataObject
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

    fun getProductDetails() = productDetails

}