package cl.tinet.demobank.ui.home.presentation

interface HomeContract {
    
    interface View {
        fun showHomeText(text: String)
        fun showLoading()
        fun hideLoading()
    }
    
    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun initView()
        fun unbindView()
        fun loadHomeContent()
    }
    
    interface Navigator {
        fun navigateToSettings()
        fun navigateToProfile()
    }
}