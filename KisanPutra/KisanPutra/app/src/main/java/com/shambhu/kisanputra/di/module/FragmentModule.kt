package com.shambhu.kisanputra.di.module

import com.shambhu.kisanputra.base.BaseFragment
import com.shambhu.kisanputra.ui.fragments.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

   /* @ContributesAndroidInjector
    abstract fun getBaseFragment(): BaseFragment*/

    @ContributesAndroidInjector
    abstract fun getBaseFragment(): BaseFragment

    @ContributesAndroidInjector
    abstract fun getHomeFragment(): HomeFragment

}