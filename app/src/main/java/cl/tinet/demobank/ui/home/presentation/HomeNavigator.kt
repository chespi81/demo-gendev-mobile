package cl.tinet.demobank.ui.home.presentation

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
}