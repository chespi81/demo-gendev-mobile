package cl.tinet.demobank.ui.slideshow.presentation

interface SlideshowContract {
    
    interface View {
        fun showSlideshowText(text: String)
        fun showLoading()
        fun hideLoading()
    }
    
    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun initView()
        fun unbindView()
        fun loadSlideshowContent()
    }
    
    interface Navigator {
        fun navigateToSlideDetail(slideId: String)
        fun navigateToSettings()
    }
}