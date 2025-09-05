package cl.tinet.demobank.ui.cardonoff.presentation

import cl.tinet.demobank.data.model.Card
import cl.tinet.demobank.data.model.CardType
import javax.inject.Inject

class CardOnOffPresenter @Inject constructor(
    private val navigator: CardOnOffContract.Navigator
) : CardOnOffContract.Presenter {
    
    private var view: CardOnOffContract.View? = null
    
    override fun attachView(view: CardOnOffContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        this.view = null
    }
    
    override fun initView() {
        loadCards()
    }
    
    override fun unbindView() {
        detachView()
    }
    
    override fun loadCards() {
        view?.showLoading()
        
        // Mock card data matching the reference image
        val mockCards = listOf(
            Card(
                id = "1",
                cardholderName = "SCROOGE MCDUCK",
                cardType = CardType.ATM,
                maskedNumber = "•••• •••• •••• 2567",
                isEnabled = true
            ),
            Card(
                id = "2",
                cardholderName = "SCROOGE MCDUCK", 
                cardType = CardType.CASH_BACK,
                maskedNumber = "•••• •••• •••• 3456",
                isEnabled = false
            ),
            Card(
                id = "3",
                cardholderName = "SCROOGE MCDUCK",
                cardType = CardType.DEBIT,
                maskedNumber = "•••• •••• •••• 4789",
                isEnabled = true
            )
        )
        
        view?.hideLoading()
        view?.showCards(mockCards)
    }
    
    override fun onCardToggleChanged(card: Card, isEnabled: Boolean) {
        // Here you would typically save the card state to a repository/database
        // For now we just update the card object which was already done in the adapter
    }
    
    override fun onCloseClicked() {
        navigator.closeCardOnOff()
    }
}