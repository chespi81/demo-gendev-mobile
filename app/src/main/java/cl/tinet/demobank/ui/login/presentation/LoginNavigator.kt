package cl.tinet.demobank.ui.login.presentation

import android.content.Intent
import cl.tinet.demobank.MainActivity
import cl.tinet.demobank.ui.login.LoginActivity
import cl.tinet.demobank.ui.login.di.scope.LoginScope
import javax.inject.Inject

@LoginScope
class LoginNavigator @Inject constructor() : LoginContract.Navigator {
    
    private var activity: LoginActivity? = null
    
    fun setActivity(activity: LoginActivity) {
        this.activity = activity
    }
    
    override fun navigateToHome() {
        activity?.let { loginActivity ->
            val intent = Intent(loginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            loginActivity.startActivity(intent)
            loginActivity.finish()
        }
    }
}