package cl.tinet.demobank.ui.login.di.module

import cl.tinet.demobank.ui.login.data.api.LoginApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
class ApiLoginModule {
    
    @Reusable
    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }
}