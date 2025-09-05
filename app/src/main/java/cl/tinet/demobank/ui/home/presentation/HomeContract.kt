package cl.tinet.demobank.ui.home.presentation

import cl.tinet.demobank.data.model.Account

interface HomeContract {
    
    interface View {
        fun showHomeText(text: String)
        fun showAccounts(accounts: List<Account>)
        fun showLoading()
        fun hideLoading()
    }
    
    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun initView()
        fun unbindView()
        fun loadHomeContent()
        fun loadAccounts()
    }
    
    interface Navigator {
        fun navigateToSettings()
        fun navigateToProfile()
        fun navigateToAccountDetail(accountId: String)
    }
}