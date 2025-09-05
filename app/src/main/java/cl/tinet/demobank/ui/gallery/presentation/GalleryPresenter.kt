package cl.tinet.demobank.ui.gallery.presentation

import javax.inject.Inject

class GalleryPresenter @Inject constructor(
    private val navigator: GalleryContract.Navigator
) : GalleryContract.Presenter {
    
    private var view: GalleryContract.View? = null
    
    override fun attachView(view: GalleryContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        this.view = null
    }
    
    override fun initView() {
        loadGalleryContent()
    }
    
    override fun unbindView() {
        detachView()
    }
    
    override fun loadGalleryContent() {
        view?.showLoading()
        
        // Simulate loading content
        val galleryText = "Your transaction history and documents gallery"
        view?.hideLoading()
        view?.showGalleryText(galleryText)
    }
}