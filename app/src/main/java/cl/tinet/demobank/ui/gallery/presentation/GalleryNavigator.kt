package cl.tinet.demobank.ui.gallery.presentation

import androidx.fragment.app.Fragment
import javax.inject.Inject

class GalleryNavigator @Inject constructor() : GalleryContract.Navigator {
    
    private var fragment: Fragment? = null
    
    fun setFragment(fragment: Fragment) {
        this.fragment = fragment
    }
    
    override fun navigateToImageDetail(imageId: String) {
        // Implementation for navigation to image detail
    }
    
    override fun navigateToCamera() {
        // Implementation for navigation to camera
    }
}