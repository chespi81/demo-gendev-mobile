package cl.tinet.demobank.ui.login.presentation

import cl.tinet.demobank.ui.login.di.scope.LoginScope
import javax.inject.Inject

@LoginScope
class LoginPresenter @Inject constructor() : LoginContract.Presenter {
    
    private var view: LoginContract.View? = null
    
    override fun attachView(view: LoginContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        this.view = null
    }
    
    override fun unbindView() {
        detachView()
    }
    
    override fun login(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            view?.showLoginError("Usuario y contraseña son requeridos")
            return
        }
        
        view?.showLoading()
        
        // Simulamos validación simple - en un caso real sería una llamada a API
        if (isValidCredentials(username, password)) {
            view?.hideLoading()
            view?.navigateToHome()
        } else {
            view?.hideLoading()
            view?.showLoginError("Usuario o contraseña incorrectos")
        }
    }
    
    private fun isValidCredentials(username: String, password: String): Boolean {
        // Validación simple para demo - en producción sería contra un servicio real
        return username == "admin" && password == "123456"
    }
}