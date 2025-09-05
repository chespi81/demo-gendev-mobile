package cl.tinet.demobank.ui.home.presentation

import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val navigator: HomeContract.Navigator
) : HomeContract.Presenter {
    
    private var view: HomeContract.View? = null
    
    override fun attachView(view: HomeContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        this.view = null
    }
    
    override fun initView() {
        loadHomeContent()
    }
    
    override fun unbindView() {
        detachView()
    }
    
    override fun loadHomeContent() {
        view?.showLoading()
        
        // Simulate loading content
        val homeText = "Welcome to DemoBank - Your digital banking solution"
        view?.hideLoading()
        view?.showHomeText(homeText)
    }
}