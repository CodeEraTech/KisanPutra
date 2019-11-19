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
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.shambhu.social.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;



// * A simple {@link Fragment} subclass.

public class FacebookFragment extends DialogFragment {

    public static final String TAG = SocialLogin.class.getName();
    static CallbackManager callbackManager;
    private SocialLoginResultListener socialLoginResultListener;

    public static FacebookFragment getInstance() {
        return new FacebookFragment();
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
        callbackManager = CallbackManager.Factory.create();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("first_name");
        arrayList.add("last_name");
        arrayList.add("picture");
        arrayList.add("verified");
        arrayList.add("email");
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(getActivity());
        LoginManager.getInstance().registerCallback
                (callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {

                                if (socialLoginResultListener != null) {
                                    GraphRequest request_graph = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                            new
                                                    GraphRequest.GraphJSONObjectCallback() {
                                                        @Override
                                                        public void onCompleted(JSONObject object, GraphResponse response) {
                                                            try {

                                                                UserModel userModel = new UserModel();
                                                                userModel.setEmail(object.optString("email"));
                                                                userModel.setFirst_name(object.optString("first_name"));
                                                                userModel.setLast_name(object.optString("last_name"));
                                                                userModel.setId(object.optString("id"));
                                                                userModel.setName(userModel.getFirst_name() + " " + userModel.getLast_name());
                                                                userModel.setVerified(object.optBoolean("verified"));
                                                                userModel.setSocialLoginType(SocialLoginType.FACEBOOK);
                                                                String profile_image = "http://graph.facebook.com/" + userModel.getId() + "/picture?type=square&height=400&width=400";
                                                                userModel.setProfile_picture(profile_image);
                                                                userModel.setMobile_no(null);
                                                                socialLoginResultListener.onLoginSuccess(userModel);
                                                            } catch (Exception e) {

                                                            }
                                                        }
                                                    });
                                    Bundle parameters = new Bundle();
                                    parameters.putString("fields", "id,name,link,first_name,last_name,email,verified");
                                    request_graph.setParameters(parameters);
                                    request_graph.executeAsync();

                                    FacebookFragment.this.dismiss();
                                }
                                // App code
                                Log.e("Fb AKM", loginResult.getAccessToken().getUserId());
                            }

                            @Override
                            public void onCancel() {
                                // App code
                                socialLoginResultListener.onLoginFailure(SocialLoginType.FACEBOOK, "Login Cancelled");
                                FacebookFragment.this.dismiss();
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                                Log.e("Fb AKM Error", exception.getMessage());
                                socialLoginResultListener.onLoginFailure(SocialLoginType.FACEBOOK, "Login Error occurred");
                                FacebookFragment.this.dismiss();
                            }
                        });

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void doFacebookLogin(FragmentManager fragmentManager, SocialLoginResultListener socialLoginResultListener) {
        this.socialLoginResultListener = socialLoginResultListener;
        show(fragmentManager, "one");
    }


}
