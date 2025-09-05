package cl.tinet.demobank.ui.slideshow.presentation

import androidx.fragment.app.Fragment
import javax.inject.Inject

class SlideshowNavigator @Inject constructor() : SlideshowContract.Navigator {
    
    private var fragment: Fragment? = null
    
    fun setFragment(fragment: Fragment) {
        this.fragment = fragment
    }
    
    override fun navigateToSlideDetail(slideId: String) {
        // Implementation for navigation to slide detail
    }
    
    override fun navigateToSettings() {
        // Implementation for navigation to settings
    }
}