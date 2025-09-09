package cl.tinet.demobank.ui.login.domain

data class LoginResponse(
    val success: Boolean,
    val token: String?,
    val message: String?
)