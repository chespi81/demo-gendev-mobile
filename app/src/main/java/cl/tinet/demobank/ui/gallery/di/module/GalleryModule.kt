package cl.tinet.demobank.ui.gallery.di.module

import cl.tinet.demobank.ui.gallery.di.scope.GalleryScope
import cl.tinet.demobank.ui.gallery.presentation.GalleryContract
import cl.tinet.demobank.ui.gallery.presentation.GalleryNavigator
import cl.tinet.demobank.ui.gallery.presentation.GalleryPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class GalleryModule {
    
    @GalleryScope
    @Binds
    abstract fun provideGalleryPresenter(presenter: GalleryPresenter): GalleryContract.Presenter
    
    @GalleryScope
    @Binds
    abstract fun provideGalleryNavigator(navigator: GalleryNavigator): GalleryContract.Navigator
}