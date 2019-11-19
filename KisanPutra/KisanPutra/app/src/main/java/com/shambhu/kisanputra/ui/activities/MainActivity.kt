package com.shambhu.kisanputra.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.provider.SyncStateContract.Helpers.update
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.location.LocationManager
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.shambhu.kisanputra.base.BaseActivity
import com.shambhu.kisanputra.mPrefs
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import androidx.core.os.HandlerCompat.postDelayed




class MainActivity : BaseActivity() {

    val handler = Handler()
    var count = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.shambhu.kisanputra.R.layout.activity_main)

        printKeyHash()
        handler.post(runnable);


//        Handler().postDelayed(object  : Runnable{
//            override fun run() {
//                if(!TextUtils.isEmpty(mPrefs.prefAuthToken) && mPrefs.prefUserDetails != null){
//
//
//                    /** User has already logged in **/
//                    startActivity(Intent(this@MainActivity,HomeActivity::class.java))
//                }else{
//
//                    startActivity(Intent(this@MainActivity,TourMain::class.java))
//                }
//
//            }
//        },2000)

    }
    val runnable: Runnable = object : Runnable
    {
        override fun run()
        {

            if (checkLocation())
            {
                if(!TextUtils.isEmpty(mPrefs.prefAuthToken) && mPrefs.prefUserDetails != null){

                    checkLocation()
                    /** User has already logged in **/
                    startActivity(Intent(this@MainActivity,HomeActivity::class.java))
                }else{
                    checkLocation()
                    startActivity(Intent(this@MainActivity,TourMain::class.java))
                }

            } else {
                handler.postDelayed(this, 2000)
            }


            // need to do tasks on the UI thread
        }
    }

    fun printKeyHash(){
        // Add code to print out the key hash
        try {
            val info = packageManager.getPackageInfo(
                "com.shambhu.kisanputra",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }



    }

}
