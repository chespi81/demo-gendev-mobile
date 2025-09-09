package cl.tinet.demobank.ui.login.domain

interface LoginRepository {
    suspend fun login(request: LoginRequest): LoginResponse
}