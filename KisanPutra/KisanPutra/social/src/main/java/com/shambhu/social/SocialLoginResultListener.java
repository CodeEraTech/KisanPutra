package com.shambhu.social;

/**
 * Created by Adminis on 1/11/2018.
 */

public interface SocialLoginResultListener {
    //    void onLoginSuccess(SocialLoginType type, GraphResponse graphResponse);
//    void onLoginSuccess(SocialLoginType type, GoogleSignInAccount loginResult);
    void onLoginSuccess(UserModel userModel);
    void onLoginFailure(SocialLoginType type, String reason);
}
