package com.shambhu.kisanputra.viewmodels

import androidx.lifecycle.MutableLiveData
import com.konstantinfo.struct.repositories.UserDetailsRepository
import com.shambhu.kisanputra.base.BaseViewModel
import com.shambhu.kisanputra.data.models.UserData
import com.shambhu.kisanputra.data.rest_api.apis.AppApiService
import com.shambhu.kisanputra.data.rest_api.request.LoginRequest
import com.shambhu.kisanputra.data.rest_api.request.SignUpRequest
import com.shambhu.kisanputra.data.rest_api.response.BaseResponse
import com.shambhu.kisanputra.data.rest_api.response.JsonObjectResponse
import com.shambhu.social.UserModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(appApiService: AppApiService) : BaseViewModel() {

    private val userDetailRepo : UserDetailsRepository = UserDetailsRepository(appApiService)
    private val checkRegisterLivedata = MutableLiveData<BaseResponse>()
    private val registerLiveData = MutableLiveData<UserData>()
    private val userDetails = MutableLiveData<UserData>()
    private val updateStatus = MutableLiveData<BaseResponse>()

    fun checkRegister(email: String?,mobile_no: String?){
        loading.value = true
        dataLoadError.value = ""
       // checkRegisterLivedata.postValue(BaseResponse())

        var tempObservable : Observable<BaseResponse>
        if (email!=null && mobile_no!=null)
            tempObservable=userDetailRepo.check_user_id_both(email,mobile_no)
        else if (email==null)
            tempObservable=userDetailRepo.check_user_id_phone(mobile_no!!)
        else
            tempObservable=userDetailRepo.check_user_id_email(email!!)

        tempObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                    if(result != null){
                        checkRegisterLivedata.value = result!!

                    }else{
                        dataLoadError.value = result
                    }
                loading.value = false
            }, { error ->
                handleError(error)
                loading.value = false
            })

    }

    fun socialRegister(userModel: UserModel,type : String){
        loading.value = true
        dataLoadError.value = ""

        var tempObservable : Observable<JsonObjectResponse<UserData>>
        if (userModel.email!=null && userModel.mobile_no!=null)
            tempObservable=userDetailRepo.registerUser_withBoth(userModel,type)
        else if (userModel.email==null)
            if (userModel.name!=null)
            tempObservable=userDetailRepo.registerUser_withMobile(userModel,type)
        else
                tempObservable=userDetailRepo.registerUser_withMobileNoname(userModel,type)
        else
            tempObservable=userDetailRepo.registerUser_withEmail(userModel,type)

        tempObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result != null){
                    if (result.status){
                        registerLiveData.value=result!!.dataObject
                    }else{
                        dataLoadError.value = result!!.message
                    }

                }else{
                    // handle null res from api
                }
                loading.value = false
            }, { error ->
                handleError(error)
                loading.value = false
            })

    }

    /*fun fetchUserDetails(userId: String){
        loading.value = true
        dataLoadError.value = ""
        userDetailRepo.fetchUserDetails(userId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->
                if(result.status){
                    if(result.dataObject != null){
                        userDetails.value = UserDetailsModel("", result!!.dataObject!!)
                    }else{
                        dataLoadError.value = result.message
                    }
                }else{
                    dataLoadError.value = result.message
                }
                loading.value = false
            }, { error ->
                handleError(error)
                loading.value = false
            })

    }*/

    fun getUpdateStatus() = updateStatus

    fun getCheckRegisterLivedata() = checkRegisterLivedata

    fun getRegisterLiveData() = registerLiveData

    fun getUserDetails() = userDetails

}