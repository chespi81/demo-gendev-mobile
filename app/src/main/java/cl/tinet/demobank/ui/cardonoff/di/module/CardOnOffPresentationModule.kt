package cl.tinet.demobank.ui.cardonoff.di.module

import cl.tinet.demobank.ui.cardonoff.CardOnOffFragment
import cl.tinet.demobank.ui.cardonoff.di.scope.CardOnOffScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CardOnOffPresentationModule {
    
    @CardOnOffScope
    @ContributesAndroidInjector(modules = [CardOnOffModule::class])
    abstract fun contributeCardOnOffFragment(): CardOnOffFragment
}