package cl.tinet.demobank.ui.home.presentation

import android.widget.Toast
import androidx.fragment.app.Fragment
import javax.inject.Inject

class HomeNavigator @Inject constructor() : HomeContract.Navigator {
    
    private var fragment: Fragment? = null
    
    fun setFragment(fragment: Fragment) {
        this.fragment = fragment
    }
    
    override fun navigateToSettings() {
        // Implementation for navigation to settings
        // This could use Navigation Component or manual fragment transactions
    }
    
    override fun navigateToProfile() {
        // Implementation for navigation to profile
        // This could use Navigation Component or manual fragment transactions
    }
    
    override fun navigateToAccountDetail(accountId: String) {
        // For now, show a toast with the account ID
        // In a real app, this would navigate to account detail screen
        fragment?.context?.let { context ->
            Toast.makeText(context, "Navigate to account: $accountId", Toast.LENGTH_SHORT).show()
        }
    }
}