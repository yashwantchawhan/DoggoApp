package com.yashwant.doggoapp.di

import android.app.Application
import android.content.Context
import com.yashwant.doggo_api.di.DoggoRepoModule
import com.yashwant.doggo_api_ui.di.DoggoCommonDependencies
import com.yashwant.doggoapp.app.DoggoApp
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

@AppScope
@Component(
    modules = [AppModule::class]
)
interface AppComponent : DoggoCommonDependencies {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: DoggoApp)
}

@Module(
    includes = [
        DoggoRepoModule::class,
        NetworkModule::class
    ]
)
object AppModule {

}