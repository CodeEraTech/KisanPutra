package com.shambhu.kisanputra

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.fragment.app.Fragment
import com.shambhu.kisanputra.di.component.DaggerAppComponent
import com.shambhu.kisanputra.di.module.ApiModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import com.shambhu.kisanputra.utils.AppPrefs
import com.shambhu.kisanputra.utils.LocaleHelper
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector

/**  Shared preference Instance to be used throughout the app directly **/
val mPrefs: AppPrefs by lazy {
    KisanPutraApp.mPrefs!!
}

/*
 * we use our AppComponent (now prefixed with Dagger)
 * to inject our Application class.
 * This way a DispatchingAndroidInjector is injected which is
 * then returned when an injector for an activity is requested.
 * */

class KisanPutraApp : Application(), HasActivityInjector , HasSupportFragmentInjector {

    companion object {
        lateinit var mPrefs: AppPrefs
//        public var mPrefs: AppPrefs? = null
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector;
    }

    override fun onCreate() {
        mPrefs = AppPrefs(this)
        super.onCreate()



        DaggerAppComponent.builder()
            .application(this)
            .apiModule(ApiModule())
            .build()
            .inject(this)

        /*try{
            applicationContext.startService(Intent(applicationContext, TransferService::class.java))
        }catch (ex : Exception){
            Log.d(KisanPutraApp::class.java.name, "Error = " + ex.toString())
        }*/

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleHelper.setLocale(this, newConfig.locale.getLanguage())
        Log.d(KisanPutraApp::class.simpleName, "onConfigurationChanged: " + newConfig.locale.getLanguage())
    }

}