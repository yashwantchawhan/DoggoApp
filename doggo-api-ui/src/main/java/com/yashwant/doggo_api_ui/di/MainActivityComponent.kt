package com.yashwant.doggo_api_ui.di

import com.yashwant.doggo_api_ui.view.MainActivity
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Scope
annotation class ActivityScope

@ActivityScope
@Component(
    modules = [ActivityModule::class],
    dependencies = [DoggoCommonDependencies::class]
)
interface MainActivityComponent  {

    @Component.Builder
    interface Builder {

        fun dependencies(doggoCommonDependencies: DoggoCommonDependencies) : Builder

        fun build(): MainActivityComponent
    }

    fun inject(activity: MainActivity)
}

@Module
object ActivityModule {

}