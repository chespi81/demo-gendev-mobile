package cl.tinet.demobank.ui.login.data

import cl.tinet.demobank.ui.login.domain.LoginRepository
import cl.tinet.demobank.ui.login.domain.LoginRequest
import cl.tinet.demobank.ui.login.domain.LoginResponse
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSource
) : LoginRepository {
    
    override suspend fun login(request: LoginRequest): LoginResponse {
        return dataSource.login(request)
    }
}