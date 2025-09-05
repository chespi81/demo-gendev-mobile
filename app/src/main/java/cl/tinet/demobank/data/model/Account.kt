package cl.tinet.demobank.data.model

data class Account(
    val id: String,
    val type: String,
    val balance: Double,
    val currency: String = "USD",
    val isVisible: Boolean = true
) {
    val formattedBalance: String
        get() = "$currency $${String.format("%,.2f", balance)}"
}