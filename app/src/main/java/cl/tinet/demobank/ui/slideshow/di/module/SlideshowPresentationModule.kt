package cl.tinet.demobank.ui.slideshow.di.module

import cl.tinet.demobank.ui.slideshow.SlideshowFragment
import cl.tinet.demobank.ui.slideshow.di.scope.SlideshowScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SlideshowPresentationModule {
    
    @SlideshowScope
    @ContributesAndroidInjector(modules = [SlideshowModule::class])
    abstract fun contributeSlideshowFragment(): SlideshowFragment
}