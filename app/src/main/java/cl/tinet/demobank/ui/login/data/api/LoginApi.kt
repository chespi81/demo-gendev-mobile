package cl.tinet.demobank.ui.login.data.api

import cl.tinet.demobank.ui.login.data.model.LoginRequestModel
import cl.tinet.demobank.ui.login.data.model.LoginResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequestModel): Response<LoginResponseModel>
}