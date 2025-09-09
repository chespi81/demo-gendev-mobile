package cl.tinet.demobank.ui.login.data

import cl.tinet.demobank.ui.login.data.api.LoginApi
import cl.tinet.demobank.ui.login.data.model.LoginRequestModel
import cl.tinet.demobank.ui.login.domain.LoginRequest
import cl.tinet.demobank.ui.login.domain.LoginResponse
import javax.inject.Inject

class LoginDataSourceRemote @Inject constructor(
    private val api: LoginApi
) : LoginDataSource {
    
    override suspend fun login(request: LoginRequest): LoginResponse {
        return try {
            val requestModel = LoginRequestModel(
                username = request.username,
                password = request.password
            )
            
            val response = api.login(requestModel)
            
            if (response.isSuccessful) {
                response.body()?.toDomain() ?: LoginResponse(
                    success = false,
                    token = null,
                    message = "Empty response from server"
                )
            } else {
                LoginResponse(
                    success = false,
                    token = null,
                    message = "HTTP ${response.code()}: ${response.message()}"
                )
            }
        } catch (e: Exception) {
            LoginResponse(
                success = false,
                token = null,
                message = e.message ?: "Network error occurred"
            )
        }
    }
}