package cl.tinet.demobank.data.model

data class Card(
    val id: String,
    val cardholderName: String,
    val cardType: CardType,
    val maskedNumber: String,
    var isEnabled: Boolean = true
)

enum class CardType(val displayName: String, val backgroundColor: String) {
    ATM("ATM", "#1E88E5"),
    CASH_BACK("CASH BACK", "#2E7D32"), 
    DEBIT("DEBIT", "#1565C0")
}