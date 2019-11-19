package com.shambhu.kisanputra.di.module

import com.shambhu.kisanputra.ui.activities.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun geMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun getLoginActivity(): LoginActivity

     @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun getDetail_Otp_Activity(): Detail_Otp_Activity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun getDetail_Name_Activity(): Detail_Email_Activity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun getHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun getDetail_Address_Activity(): Detail_Address_Activity

}