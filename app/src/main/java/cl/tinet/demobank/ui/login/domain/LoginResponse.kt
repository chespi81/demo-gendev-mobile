package cl.tinet.demobank.ui.login.domain

data class LoginResponse(
    val success: Boolean,
    val token: String?,
    val message: String?,
    val userId: String? = null,
    val username: String? = null
)