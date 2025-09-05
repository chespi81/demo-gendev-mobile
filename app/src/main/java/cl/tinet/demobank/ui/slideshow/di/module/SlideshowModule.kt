package cl.tinet.demobank.ui.slideshow.di.module

import cl.tinet.demobank.ui.slideshow.di.scope.SlideshowScope
import cl.tinet.demobank.ui.slideshow.presentation.SlideshowContract
import cl.tinet.demobank.ui.slideshow.presentation.SlideshowNavigator
import cl.tinet.demobank.ui.slideshow.presentation.SlideshowPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class SlideshowModule {
    
    @SlideshowScope
    @Binds
    abstract fun provideSlideshowPresenter(presenter: SlideshowPresenter): SlideshowContract.Presenter
    
    @SlideshowScope
    @Binds
    abstract fun provideSlideshowNavigator(navigator: SlideshowNavigator): SlideshowContract.Navigator
}