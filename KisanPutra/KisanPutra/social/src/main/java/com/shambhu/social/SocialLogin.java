package com.shambhu.social;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.login.LoginManager;
import com.shambhu.social.fragment.FacebookFragment;
import com.shambhu.social.fragment.GoogleFragment;
import com.shambhu.social.fragment.TrueCallerFragment;

/**
 * Created by Adminis on 1/10/2018.
 */

public class SocialLogin {

    static FacebookFragment mFacebookFragment;
    public static void setFaceBookFragment(AppCompatActivity _activity, SocialLoginResultListener socialLoginResultListener){
        FacebookFragment.getInstance().doFacebookLogin(_activity.getSupportFragmentManager(),socialLoginResultListener);

    }
    public static void setGoogleFragment(AppCompatActivity _activity, SocialLoginResultListener
            socialLoginResultListener){
        GoogleFragment.getInstance().doGoogleLogin(_activity.getSupportFragmentManager(),
                 socialLoginResultListener);

    }

    public static void setTrueCallerFragment(AppCompatActivity _activity, SocialLoginResultListener
            socialLoginResultListener){
       // _activity.getSupportFragmentManager().beginTransaction().add(TrueCallerFragment.getInstance(),"one").commit();
        TrueCallerFragment.getInstance().doTruecallerLogin(_activity.getSupportFragmentManager(), socialLoginResultListener);
       // _activity.startActivity(new Intent(_activity,TrueCallerActivity.class));
    }

    public static void logoutFromFB(){
        LoginManager.getInstance().logOut();
    }

}
