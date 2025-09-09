package cl.tinet.demobank.di

import android.app.Application
import cl.tinet.demobank.DemoBankApplication
import cl.tinet.demobank.di.module.ActivityModule
import cl.tinet.demobank.di.module.AppModule
import cl.tinet.demobank.di.module.NetworkModule
import cl.tinet.demobank.di.module.SessionModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        NetworkModule::class,
        SessionModule::class
    ]
)
interface AppComponent {
    
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        
        fun build(): AppComponent
    }
    
    fun inject(application: DemoBankApplication)
}