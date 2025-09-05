package cl.tinet.demobank.di.module

import cl.tinet.demobank.MainActivity
import cl.tinet.demobank.ui.cardonoff.di.module.CardOnOffPresentationModule
import cl.tinet.demobank.ui.home.di.module.HomePresentationModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    
    @ContributesAndroidInjector(
        modules = [
            HomePresentationModule::class,
            CardOnOffPresentationModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}