package cl.tinet.demobank.ui.home.di.module

import cl.tinet.demobank.ui.home.HomeFragment
import cl.tinet.demobank.ui.home.di.scope.HomeScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomePresentationModule {
    
    @HomeScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeFragment(): HomeFragment
}