package com.shambhu.kisanputra.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.an.trailers.di.module.ViewModelKey
import com.shambhu.kisanputra.viewmodels.HomeViewModel
import com.shambhu.kisanputra.viewmodels.UserDetailsViewModel
import com.shambhu.kisanputra.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailsViewModel::class)
    protected abstract fun userDetailsViewModel(userDetailsViewModel: UserDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    protected abstract fun homeViewModel(userDetailsViewModel: HomeViewModel): ViewModel



}