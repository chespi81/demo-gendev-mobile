package cl.tinet.demobank.ui.login.data

import cl.tinet.demobank.ui.login.domain.LoginRequest
import cl.tinet.demobank.ui.login.domain.LoginResponse

interface LoginDataSource {
    suspend fun login(request: LoginRequest): LoginResponse
}