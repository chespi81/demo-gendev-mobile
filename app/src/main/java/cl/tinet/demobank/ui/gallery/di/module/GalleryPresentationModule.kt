package cl.tinet.demobank.ui.gallery.di.module

import cl.tinet.demobank.ui.gallery.GalleryFragment
import cl.tinet.demobank.ui.gallery.di.scope.GalleryScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GalleryPresentationModule {
    
    @GalleryScope
    @ContributesAndroidInjector(modules = [GalleryModule::class])
    abstract fun contributeGalleryFragment(): GalleryFragment
}