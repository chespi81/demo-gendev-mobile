package cl.tinet.demobank.data.session

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val PREF_NAME = "demobank_session"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_TOKEN = "user_token"
        private const val KEY_USERNAME = "username"
        private const val KEY_USER_ID = "user_id"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserSession(token: String, username: String, userId: String? = null) {
        sharedPreferences.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_USER_TOKEN, token)
            putString(KEY_USERNAME, username)
            userId?.let { putString(KEY_USER_ID, it) }
            apply()
        }
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserToken(): String? {
        return sharedPreferences.getString(KEY_USER_TOKEN, null)
    }

    fun getUsername(): String? {
        return sharedPreferences.getString(KEY_USERNAME, null)
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun clearSession() {
        sharedPreferences.edit().apply {
            clear()
            apply()
        }
    }

    fun getUserSessionData(): UserSessionData? {
        return if (isUserLoggedIn()) {
            UserSessionData(
                token = getUserToken(),
                username = getUsername(),
                userId = getUserId()
            )
        } else {
            null
        }
    }
}

data class UserSessionData(
    val token: String?,
    val username: String?,
    val userId: String?
)