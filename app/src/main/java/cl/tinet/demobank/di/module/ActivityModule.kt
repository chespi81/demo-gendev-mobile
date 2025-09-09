package cl.tinet.demobank.di.module

import cl.tinet.demobank.MainActivity
import cl.tinet.demobank.SplashActivity
import cl.tinet.demobank.ui.cardonoff.di.module.CardOnOffPresentationModule
import cl.tinet.demobank.ui.home.di.module.HomePresentationModule
import cl.tinet.demobank.ui.login.LoginActivity
import cl.tinet.demobank.ui.login.di.module.LoginPresentationModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    
    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity
    
    @cl.tinet.demobank.ui.login.di.scope.LoginScope
    @ContributesAndroidInjector(
        modules = [
            LoginPresentationModule::class
        ]
    )
    abstract fun contributeLoginActivity(): LoginActivity
    
    @ContributesAndroidInjector(
        modules = [
            HomePresentationModule::class,
            CardOnOffPresentationModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}