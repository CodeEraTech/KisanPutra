package com.shambhu.kisanputra.ui.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.facebook.accountkit.*
import com.facebook.accountkit.ui.AccountKitActivity
import com.facebook.accountkit.ui.AccountKitConfiguration
import com.facebook.accountkit.ui.LoginType
import com.facebook.accountkit.ui.SkinManager
import com.shambhu.kisanputra.R
import com.shambhu.kisanputra.base.BaseActivity
import com.shambhu.kisanputra.data.rest_api.interceptor.ConnectivityStatus
import com.shambhu.kisanputra.data.rest_api.request.LoginRequest
import com.shambhu.kisanputra.mPrefs
import com.shambhu.kisanputra.utils.StaticData
import com.shambhu.kisanputra.viewmodels.UserDetailsViewModel
import com.shambhu.kisanputra.viewmodels.ViewModelFactory
import com.shambhu.social.SocialLogin
import com.shambhu.social.SocialLoginResultListener
import com.shambhu.social.SocialLoginType
import com.shambhu.social.UserModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.internal.http.StatusLine
import javax.inject.Inject

class LoginActivity : BaseActivity() ,View.OnClickListener, SocialLoginResultListener {

    var userModel :  UserModel? = null

    override fun onLoginSuccess(userModel: UserModel?) {
        //showToast(userModel!!.first_name+" "+userModel!!.email)
        this.userModel=userModel
       // mUserViewModel.checkRegister(userModel!!.email,userModel!!.mobile_no)
        requestCheckRegister(userModel!!.email,userModel!!.mobile_no)
    }

    override fun onLoginFailure(type: SocialLoginType?, reason: String?) {
        showToast(reason!!)
    }

    @Inject
    lateinit var userdetailFactory: ViewModelFactory
    lateinit var mUserViewModel: UserDetailsViewModel

    var number : String=""

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.next_btn-> {
                if (TextUtils.isEmpty(editText.text)) {
                    editText.requestFocus()
                    editText.setError("" + getString(R.string.Enter_phone_number))
                } else if (editText.text.length < 10) {
                    editText.requestFocus()
                    editText.setError("" + getString(R.string.invalid_phone_number))
                } else {
                    hideKeyBoard()
                    number = editText.text.trim().toString()
                    phoneLogin(number)
                    //startActivity(Intent(this,Detail_Otp_Activity::class.java).putExtra("number",""+editText.text.toString()))
                }
            }
            R.id.facebook_login-> {
                hideKeyBoard()
                SocialLogin.setFaceBookFragment(this@LoginActivity, this@LoginActivity)
            }
            R.id.google_login->{
                hideKeyBoard()
                SocialLogin.setGoogleFragment(this@LoginActivity,this@LoginActivity)
            }

            R.id.truecaller_login->{
                hideKeyBoard()
                SocialLogin.setTrueCallerFragment(this@LoginActivity,this@LoginActivity)
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mUserViewModel = ViewModelProviders.of(this, userdetailFactory).get(UserDetailsViewModel::class.java)
        hideKeyBoard()
        next_btn.setOnClickListener(this)
        facebook_login.setOnClickListener(this)
        google_login.setOnClickListener(this)
        truecaller_login.setOnClickListener(this)

    }

    fun phoneLogin(phone : String) {
        val intent = Intent(this, AccountKitActivity::class.java)

        val configurationBuilder = AccountKitConfiguration.AccountKitConfigurationBuilder(
            LoginType.PHONE,
            AccountKitActivity.ResponseType.TOKEN
        ) // or .ResponseType.TOKEN
        // ... perform additional configuration ...

        val uiManager = SkinManager(
            SkinManager.Skin.CLASSIC, resources.getColor(R.color.colorPrimary),
            R.drawable.otp_screen_bg, SkinManager.Tint.BLACK, 0.0
        )

        configurationBuilder.setUIManager(uiManager)


        val number =
            PhoneNumber("+91", "" + phone, null)
        intent.putExtra(
            AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
            configurationBuilder.setInitialPhoneNumber(number).build()
        )

        startActivityForResult(intent, 99)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 99) { // confirm that this response matches your request
            val loginResult = data!!.getParcelableExtra<AccountKitLoginResult>(AccountKitLoginResult.RESULT_KEY)
            val toastMessage: String
            if (loginResult.error != null) {
                toastMessage = loginResult.error!!.errorType.message
                //showErrorActivity(loginResult.getError());
                showToast(toastMessage)
                Log.e("FbKit ", toastMessage)
            } else if (loginResult.wasCancelled()) {
                toastMessage = ""+getString(R.string.Login_Cancelled)
                showToast(toastMessage)
                Log.e("FbKit ", toastMessage)
            } else {

                AccountKit.getCurrentAccount(object : AccountKitCallback<Account> {
                    override fun onSuccess(account: Account) {
                        val accountKitId = account.id

                        // Get phone number
                        val phoneNumber = account.phoneNumber
                        if (phoneNumber != null) {
                            val phoneNumberString = phoneNumber.phoneNumber
                            if (phoneNumberString == "" + number) {
                               // loginSuccess(phoneNumberString)
                                //requestUserLogin()
                                userModel =UserModel()
                                userModel!!.email=null
                                userModel!!.mobile_no=number
                                userModel!!.socialLoginType=SocialLoginType.MOBILE
                                // mUserViewModel.checkRegister(null,phoneNumberString)
                                requestCheckRegister(null,phoneNumberString)
                            } else {
                                showToast(getString(R.string.invalid_phone_number))
                            }
                        }
                    }

                    override fun onError(accountKitError: AccountKitError) {
                        Log.e("Accountkit error ", "" + accountKitError.userFacingMessage)
                        showToast(""+accountKitError.userFacingMessage)
                    }
                })

            }
        }
    }

       private fun requestCheckRegister(email : String?, mobile_no : String?) {

        if (!ConnectivityStatus.isConnected(this)) {
            showNetworkIssue()
            return
        }

        mUserViewModel.checkRegister(email, mobile_no)
        mUserViewModel.loading.observe(this, Observer { isLoading ->
            handleProgressLoader(isLoading)
        })
        mUserViewModel.dataLoadError.observe(this, Observer { error ->
            handleError(error)
        })
        mUserViewModel.getCheckRegisterLivedata().observe(this, Observer { resource ->
            if (resource != null) {
                if (resource!!.status) {
                    /*showAlertDialog("Error",resource!!.message,"","",object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog!!.dismiss()
                        }
                    })*/
                  //  showToast(resource!!.message)
                    // Process to login
                   // if (userModel!=null)
                    requestUserLogin(getLoginType(userModel!!.socialLoginType))
                } else {
                    // not rergister  // Process to register
                    requestRegister()

                }
            } else {
                handleError(StaticData.UNDEFINED)
            }

        })
    }

    private fun requestRegister(){
        var intent: Intent=Intent(this,Detail_Name_Activity::class.java)
        intent.putExtra("userModel",userModel)
        finish()
        startActivity(intent)
    }


    fun requestUserLogin(type : String){


        if (!ConnectivityStatus.isConnected(this)) {
            showNetworkIssue()
            return
        }

        mUserViewModel.socialRegister(userModel!!, type)
        mUserViewModel.loading.observe(this, Observer { isLoading ->
            handleProgressLoader(isLoading)
        })
        mUserViewModel.dataLoadError.observe(this, Observer { error ->
            handleError(error)
        })
        mUserViewModel.getRegisterLiveData().observe(this, Observer { resource ->
            if (resource != null) {
                mPrefs.prefUserDetails=resource!!
                mPrefs.prefAuthToken=resource!!.token
                loginSuccess()
            }else {
                handleError(StaticData.UNDEFINED)
            }

        })

    }

    fun loginSuccess(phoneNumber : String){
        val intent = Intent(this@LoginActivity, Detail_Name_Activity::class.java)
        intent.putExtra("phone",phoneNumber)
        finish()
        startActivity(intent)

    }

    fun loginSuccess(){
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        finish()
        startActivity(intent)

    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//
//        if ((keyCode == KeyEvent.KEYCODE_BACK))
//        {
//            finish();
//        }
//        return super.onKeyDown(keyCode, event)
//    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }

}
