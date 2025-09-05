package cl.tinet.demobank.ui.cardonoff.presentation

import cl.tinet.demobank.data.model.Card

interface CardOnOffContract {
    
    interface View {
        fun showCards(cards: List<Card>)
        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun closeView()
    }
    
    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun initView()
        fun unbindView()
        fun loadCards()
        fun onCardToggleChanged(card: Card, isEnabled: Boolean)
        fun onCloseClicked()
    }
    
    interface Navigator {
        fun closeCardOnOff()
    }
}