package cl.tinet.demobank.ui.login.presentation

import cl.tinet.demobank.data.session.SessionManager
import cl.tinet.demobank.ui.login.di.scope.LoginScope
import cl.tinet.demobank.ui.login.domain.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@LoginScope
class LoginPresenter @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sessionManager: SessionManager
) : LoginContract.Presenter {
    
    private var view: LoginContract.View? = null
    private val presenterJob = Job()
    private val presenterScope = CoroutineScope(Dispatchers.Main + presenterJob)
    
    override fun attachView(view: LoginContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        this.view = null
    }
    
    override fun unbindView() {
        presenterJob.cancel()
        detachView()
    }
    
    override fun login(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            view?.showLoginError("Usuario y contraseña son requeridos")
            return
        }
        
        view?.showLoading()
        
        presenterScope.launch {
            try {
                val response = loginUseCase.execute(username, password)
                view?.hideLoading()
                
                if (response.success && !response.token.isNullOrEmpty()) {
                    // Save user session using username from server response
                    sessionManager.saveUserSession(
                        token = response.token,
                        username = response.username ?: username, // Use server username or fallback to form username
                        userId = response.userId
                    )
                    view?.navigateToHome()
                } else {
                    view?.showLoginError(response.message ?: "Error desconocido")
                }
            } catch (e: Exception) {
                view?.hideLoading()
                view?.showLoginError(e.message ?: "Error de conexión")
            }
        }
    }
}