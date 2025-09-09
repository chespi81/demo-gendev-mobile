package cl.tinet.demobank.ui.login.di.module

import cl.tinet.demobank.ui.login.data.LoginDataSource
import cl.tinet.demobank.ui.login.data.LoginDataSourceRemote
import cl.tinet.demobank.ui.login.data.LoginRepositoryImpl
import cl.tinet.demobank.ui.login.di.scope.LoginScope
import cl.tinet.demobank.ui.login.domain.LoginRepository
import cl.tinet.demobank.ui.login.presentation.LoginContract
import cl.tinet.demobank.ui.login.presentation.LoginNavigator
import cl.tinet.demobank.ui.login.presentation.LoginPresenter
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
abstract class LoginModule {
    
    @LoginScope
    @Binds
    abstract fun provideLoginPresenter(presenter: LoginPresenter): LoginContract.Presenter
    
    @LoginScope
    @Binds
    abstract fun provideLoginNavigator(navigator: LoginNavigator): LoginContract.Navigator
    
    @Reusable
    @Binds
    abstract fun provideLoginRepository(repository: LoginRepositoryImpl): LoginRepository
    
    @Reusable
    @Binds
    abstract fun provideLoginDataSource(dataSource: LoginDataSourceRemote): LoginDataSource
}