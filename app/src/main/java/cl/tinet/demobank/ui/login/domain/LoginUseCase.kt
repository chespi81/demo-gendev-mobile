package cl.tinet.demobank.ui.login.domain

import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend fun execute(username: String, password: String): LoginResponse {
        val request = LoginRequest(username, password)
        return repository.login(request)
    }
}