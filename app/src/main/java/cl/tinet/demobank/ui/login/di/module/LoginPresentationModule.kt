package cl.tinet.demobank.ui.login.di.module

import dagger.Module

@Module(includes = [LoginModule::class, ApiLoginModule::class])
class LoginPresentationModule