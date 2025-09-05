package cl.tinet.demobank.ui.gallery.presentation

interface GalleryContract {
    
    interface View {
        fun showGalleryText(text: String)
        fun showLoading()
        fun hideLoading()
    }
    
    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun initView()
        fun unbindView()
        fun loadGalleryContent()
    }
    
    interface Navigator {
        fun navigateToImageDetail(imageId: String)
        fun navigateToCamera()
    }
}