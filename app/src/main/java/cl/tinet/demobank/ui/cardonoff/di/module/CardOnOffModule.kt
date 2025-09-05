package cl.tinet.demobank.ui.cardonoff.di.module

import cl.tinet.demobank.ui.cardonoff.di.scope.CardOnOffScope
import cl.tinet.demobank.ui.cardonoff.presentation.CardOnOffContract
import cl.tinet.demobank.ui.cardonoff.presentation.CardOnOffNavigator
import cl.tinet.demobank.ui.cardonoff.presentation.CardOnOffPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class CardOnOffModule {
    
    @CardOnOffScope
    @Binds
    abstract fun provideCardOnOffPresenter(presenter: CardOnOffPresenter): CardOnOffContract.Presenter
    
    @CardOnOffScope
    @Binds
    abstract fun provideCardOnOffNavigator(navigator: CardOnOffNavigator): CardOnOffContract.Navigator
}