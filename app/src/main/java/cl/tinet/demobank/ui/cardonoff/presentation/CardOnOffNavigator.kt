package cl.tinet.demobank.ui.cardonoff.presentation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import javax.inject.Inject

class CardOnOffNavigator @Inject constructor() : CardOnOffContract.Navigator {
    
    private var fragment: Fragment? = null
    
    fun setFragment(fragment: Fragment) {
        this.fragment = fragment
    }
    
    override fun closeCardOnOff() {
        fragment?.findNavController()?.navigateUp()
    }
}