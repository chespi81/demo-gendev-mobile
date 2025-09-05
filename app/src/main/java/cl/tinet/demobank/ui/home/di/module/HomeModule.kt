package cl.tinet.demobank.ui.home.di.module

import cl.tinet.demobank.ui.home.di.scope.HomeScope
import cl.tinet.demobank.ui.home.presentation.HomeContract
import cl.tinet.demobank.ui.home.presentation.HomeNavigator
import cl.tinet.demobank.ui.home.presentation.HomePresenter
import dagger.Binds
import dagger.Module

@Module
abstract class HomeModule {
    
    @HomeScope
    @Binds
    abstract fun provideHomePresenter(presenter: HomePresenter): HomeContract.Presenter
    
    @HomeScope
    @Binds
    abstract fun provideHomeNavigator(navigator: HomeNavigator): HomeContract.Navigator
}