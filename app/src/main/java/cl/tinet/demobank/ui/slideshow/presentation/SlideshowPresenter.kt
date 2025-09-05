package cl.tinet.demobank.ui.slideshow.presentation

import javax.inject.Inject

class SlideshowPresenter @Inject constructor(
    private val navigator: SlideshowContract.Navigator
) : SlideshowContract.Presenter {
    
    private var view: SlideshowContract.View? = null
    
    override fun attachView(view: SlideshowContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        this.view = null
    }
    
    override fun initView() {
        loadSlideshowContent()
    }
    
    override fun unbindView() {
        detachView()
    }
    
    override fun loadSlideshowContent() {
        view?.showLoading()
        
        // Simulate loading content
        val slideshowText = "Financial tips and banking tutorials slideshow"
        view?.hideLoading()
        view?.showSlideshowText(slideshowText)
    }
}