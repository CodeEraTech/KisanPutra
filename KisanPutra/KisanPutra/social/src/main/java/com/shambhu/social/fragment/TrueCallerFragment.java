package com.shambhu.social.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.shambhu.social.*;
import com.truecaller.android.sdk.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;



// * A simple {@link Fragment} subclass.

public class TrueCallerFragment extends DialogFragment {

    public static final String TAG = "Social login Truecaller";
    private SocialLoginResultListener socialLoginResultListener;

    private ITrueCallback sdkCallback;


    public static TrueCallerFragment getInstance() {
        return new TrueCallerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_facebook, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        start();
    }

    public void start() {

        // check whether the SDK can be used or not


            sdkCallback = new ITrueCallback() {

                @Override
                public void onSuccessProfileShared(@NonNull final TrueProfile trueProfile) {

                    // This method is invoked when the truecaller app is installed on the device and the user gives his
                    // consent to share his truecaller profile

                    Log.d( TAG, "Verified Successfully : " + trueProfile.firstName );

                    UserModel userModel = new UserModel();
                    userModel.setEmail(trueProfile.email);
                    userModel.setFirst_name(trueProfile.firstName);
                    userModel.setLast_name(trueProfile.lastName);
                    userModel.setId(trueProfile.phoneNumber);
                    userModel.setName(userModel.getFirst_name() + " " + userModel.getLast_name());
                    userModel.setVerified(true);
                    userModel.setSocialLoginType(SocialLoginType.TRUECALLER);
                    String profile_image = trueProfile.avatarUrl;
                    userModel.setProfile_picture(profile_image);
                    socialLoginResultListener.onLoginSuccess(userModel);
                    TrueCallerFragment.this.dismiss();
                }

                @Override
                public void onFailureProfileShared(@NonNull final TrueError trueError) {
                    // This method is invoked when some error occurs or if an invalid request for verification is made

                    Log.d( TAG, "onFailureProfileShared: " + trueError.getErrorType() );
                    socialLoginResultListener.onLoginFailure(SocialLoginType.TRUECALLER, ""+trueError.toString());
                    TrueCallerFragment.this.dismiss();
                }

                @Override
                public void onVerificationRequired() {
                    Log.d( TAG, "Verification Require" );
                    socialLoginResultListener.onLoginFailure(SocialLoginType.TRUECALLER, "Verification Require");
                    TrueCallerFragment.this.dismiss();
                }


            };

        TrueSdkScope trueScope = new TrueSdkScope.Builder(this.getContext(), sdkCallback)
                .consentMode(TrueSdkScope.CONSENT_MODE_FULLSCREEN )
                .consentTitleOption( TrueSdkScope.SDK_CONSENT_TITLE_VERIFY )
                .footerType( TrueSdkScope.FOOTER_TYPE_SKIP )
                .build();

        TrueSDK.init(trueScope);

        if (TrueSDK.getInstance().isUsable()) {
            TrueSDK.getInstance().getUserProfile(getActivity());
        }else {
            socialLoginResultListener.onLoginFailure(SocialLoginType.TRUECALLER, "True caller not installed");
            TrueCallerFragment.this.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        TrueSDK.getInstance().onActivityResultObtained( this.getActivity(),resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void doTruecallerLogin(FragmentManager fragmentManager, SocialLoginResultListener socialLoginResultListener) {
        this.socialLoginResultListener = socialLoginResultListener;
        show(fragmentManager, "one");
    }


}
