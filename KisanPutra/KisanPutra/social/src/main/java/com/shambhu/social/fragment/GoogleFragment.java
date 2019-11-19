package com.shambhu.social.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.shambhu.social.R;
import com.shambhu.social.SocialLoginResultListener;
import com.shambhu.social.SocialLoginType;
import com.shambhu.social.UserModel;


/**
 * Created by Adminis on 1/11/2018.
 */

public class GoogleFragment extends DialogFragment {

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    Context mContext;
    private int RC_sign_in = 1000;
    private SocialLoginResultListener socialLoginResultListener;
    public static GoogleFragment getInstance(){
        return new GoogleFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
     //   GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mContext);
       // updateUI(account);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.server_client_id))
                .requestEmail().requestProfile().build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        start(getActivity());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      //  mContext = context;
    }

    public void start(Context context) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_sign_in);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_sign_in){
            com.google.android.gms.tasks.Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(com.google.android.gms.tasks.Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null){

                UserModel userModel = new UserModel();
                userModel.setId(account.getId());
                userModel.setName(account.getDisplayName());
                userModel.setEmail(account.getEmail());
                userModel.setMobile_no(null);
                userModel.setProfile_picture(account.getPhotoUrl() != null? account.getPhotoUrl().toString(): " ");
                userModel.setSocialLoginType(SocialLoginType.GOOGLE);



             socialLoginResultListener.onLoginSuccess(userModel);
            Log.e(GoogleFragment.class.getSimpleName(),"value of the google sign up is " +
                    " display name:"+account.getDisplayName()+" , id :"+account.getId()
            +" photo uri :"+account.getPhotoUrl()+", email :"+account.getEmail());

            }else{
                socialLoginResultListener.onLoginFailure(SocialLoginType.GOOGLE,"response null");
            }
            dismiss();
        } catch (ApiException  e) {
            socialLoginResultListener.onLoginFailure(SocialLoginType.GOOGLE,String .valueOf(e.getStatusCode()));
            dismiss();
        }

    }

    public void doGoogleLogin(FragmentManager fragmentManager, SocialLoginResultListener socialLoginResultListener){
        this.socialLoginResultListener = socialLoginResultListener;
        show(fragmentManager,"one");
    }
}

/*
    Client ID
305849703993-au43d2ioovsdafvg6t0n5cpu8sogiecp.apps.googleusercontent.com

        Client Secret
        fAVCr4r1vZ8yyWFuj880nlfK
        */
