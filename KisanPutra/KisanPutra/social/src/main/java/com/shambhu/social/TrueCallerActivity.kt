package com.shambhu.social

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.truecaller.android.sdk.*

class TrueCallerActivity : AppCompatActivity() {

    val TAG = "Social login Truecaller"
    private var socialLoginResultListener: SocialLoginResultListener? = null

    private var sdkCallback: ITrueCallback? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_true_caller)
        start()

    }

    fun start() {

        sdkCallback = object : ITrueCallback {

            override fun onSuccessProfileShared(trueProfile: TrueProfile) {

                // This method is invoked when the truecaller app is installed on the device and the user gives his
                // consent to share his truecaller profile

                Log.d(TAG, "Verified Successfully : " + trueProfile.firstName)

                val userModel = UserModel()
                userModel.setEmail(trueProfile.email)
                userModel.setFirst_name(trueProfile.firstName)
                userModel.setLast_name(trueProfile.lastName)
                userModel.setId(trueProfile.phoneNumber)
                userModel.setName(userModel.getFirst_name() + " " + userModel.getLast_name())
                userModel.setVerified(true)
                userModel.socialLoginType = SocialLoginType.TRUECALLER
                val profile_image = trueProfile.avatarUrl
                userModel.setProfile_picture(profile_image)
                socialLoginResultListener!!.onLoginSuccess(userModel)
                finish()

            }

            override fun onFailureProfileShared(trueError: TrueError) {
                // This method is invoked when some error occurs or if an invalid request for verification is made

                Log.d(TAG, "FailureProfileShared: " + trueError.errorType)
                socialLoginResultListener!!.onLoginFailure(SocialLoginType.TRUECALLER, "" + trueError.toString())
            }

            override fun onVerificationRequired() {
                Log.d(TAG, "Verification Require")
                socialLoginResultListener!!.onLoginFailure(SocialLoginType.TRUECALLER, "Verification Require")
            }


        }

        val trueScope = TrueSdkScope.Builder(this, sdkCallback!!)
            .consentMode(TrueSdkScope.CONSENT_MODE_FULLSCREEN)
            .consentTitleOption(TrueSdkScope.SDK_CONSENT_TITLE_VERIFY)
            .footerType(TrueSdkScope.FOOTER_TYPE_SKIP)
            .build()

        TrueSDK.init(trueScope)

        // check whether the SDK can be used or not


        if (TrueSDK.getInstance().isUsable) {
            TrueSDK.getInstance().getUserProfile(this)
        } else {
            socialLoginResultListener!!.onLoginFailure(SocialLoginType.TRUECALLER, "True caller not installed")
        }
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        TrueSDK.getInstance().onActivityResultObtained(this, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

}
