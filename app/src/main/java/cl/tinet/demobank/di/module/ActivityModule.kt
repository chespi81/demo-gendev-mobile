package cl.tinet.demobank.di.module

import cl.tinet.demobank.MainActivity
import cl.tinet.demobank.ui.gallery.di.module.GalleryPresentationModule
import cl.tinet.demobank.ui.home.di.module.HomePresentationModule
import cl.tinet.demobank.ui.slideshow.di.module.SlideshowPresentationModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    
    @ContributesAndroidInjector(
        modules = [
            HomePresentationModule::class,
            GalleryPresentationModule::class,
            SlideshowPresentationModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}