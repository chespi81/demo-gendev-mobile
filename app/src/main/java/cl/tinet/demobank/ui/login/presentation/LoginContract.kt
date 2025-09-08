package cl.tinet.demobank.ui.login.presentation

interface LoginContract {
    
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showLoginError(message: String)
        fun clearFields()
        fun navigateToHome()
    }
    
    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun login(username: String, password: String)
        fun unbindView()
    }
    
    interface Navigator {
        fun navigateToHome()
    }
}