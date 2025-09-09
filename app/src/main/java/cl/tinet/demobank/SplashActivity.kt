package cl.tinet.demobank

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cl.tinet.demobank.data.session.SessionManager
import cl.tinet.demobank.databinding.ActivitySplashBinding
import cl.tinet.demobank.ui.login.LoginActivity
import dagger.android.AndroidInjection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check session after a small delay for better UX
        lifecycleScope.launch {
            delay(2000) // 2 seconds splash screen
            checkUserSession()
        }
    }

    private fun checkUserSession() {
        if (sessionManager.isUserLoggedIn()) {
            // User is logged in, go to main activity
            navigateToMainActivity()
        } else {
            // User is not logged in, go to login activity
            navigateToLoginActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}