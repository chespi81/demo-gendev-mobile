package cl.tinet.demobank.ui.home.presentation

import cl.tinet.demobank.data.model.Account
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val navigator: HomeContract.Navigator
) : HomeContract.Presenter {
    
    private var view: HomeContract.View? = null
    
    override fun attachView(view: HomeContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        this.view = null
    }
    
    override fun initView() {
        loadHomeContent()
        loadAccounts()
    }
    
    override fun unbindView() {
        detachView()
    }
    
    override fun loadHomeContent() {
        val homeText = "Mis cuentas"
        view?.showHomeText(homeText)
    }
    
    override fun loadAccounts() {
        view?.showLoading()
        
        // Mock account data matching the reference image
        val mockAccounts = listOf(
            Account(
                id = "1",
                type = "Cuenta Sueldo",
                balance = 9560.00,
                currency = "$ /"
            ),
            Account(
                id = "2", 
                type = "Cuenta Free",
                balance = 152.00,
                currency = "$ /"
            ),
            Account(
                id = "3",
                type = "Visa Signature",
                balance = 1500.00,
                currency = "US$ "
            ),
            Account(
                id = "4",
                type = "Préstamos",
                balance = 2457.23,
                currency = "US$ "
            )
        )
        
        view?.hideLoading()
        view?.showAccounts(mockAccounts)
    }
}