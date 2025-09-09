package cl.tinet.demobank.di.module

import android.content.Context
import cl.tinet.demobank.data.session.SessionManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SessionModule {

    @Provides
    @Singleton
    fun provideSessionManager(context: Context): SessionManager {
        return SessionManager(context)
    }
}