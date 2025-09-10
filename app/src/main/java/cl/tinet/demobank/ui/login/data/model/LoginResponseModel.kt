package cl.tinet.demobank.ui.login.data.model

import cl.tinet.demobank.ui.login.domain.LoginResponse
import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    @SerializedName("authenticated")
    val authenticated: Boolean?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("ownerId")
    val ownerId: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("error")
    val error: String?
) {
    fun toDomain(): LoginResponse {
        return LoginResponse(
            success = authenticated ?: false,
            token = token,
            message = message ?: error,
            userId = ownerId,
            username = username
        )
    }
}