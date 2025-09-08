package cl.tinet.demobank.ui.login.di.module

import cl.tinet.demobank.ui.login.di.scope.LoginScope
import cl.tinet.demobank.ui.login.presentation.LoginContract
import cl.tinet.demobank.ui.login.presentation.LoginNavigator
import cl.tinet.demobank.ui.login.presentation.LoginPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class LoginModule {
    
    @LoginScope
    @Binds
    abstract fun provideLoginPresenter(presenter: LoginPresenter): LoginContract.Presenter
    
    @LoginScope
    @Binds
    abstract fun provideLoginNavigator(navigator: LoginNavigator): LoginContract.Navigator
}