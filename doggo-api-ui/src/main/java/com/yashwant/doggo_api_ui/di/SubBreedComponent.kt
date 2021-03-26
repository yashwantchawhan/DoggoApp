package com.yashwant.doggo_api_ui.di

import android.app.Activity
import com.yashwant.doggo_api_bridge.repository.DoggoRepository
import com.yashwant.doggo_api_bridge.scheduler.SchedulerProvider
import com.yashwant.doggo_api_ui.view.subbreed.SubBreedFragment
import com.yashwant.doggo_api_ui.view.subbreed.SubBreedViewModel
import com.yashwant.doggo_api_ui.view.subbreed.SubBreedViewModelImpl
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
    modules = [SubBreedModule::class],
    dependencies = [DoggoCommonDependencies::class]
)
interface SubBreedComponent {
    @Component.Factory
    interface Factory {
        fun create(
            doggoCommonDependencies: DoggoCommonDependencies,
            @BindsInstance activity: Activity,
        ): SubBreedComponent
    }

    fun inject(fragment: SubBreedFragment)
}

@Module
object SubBreedModule {

    @Provides
    fun subBreedViewModel(
        doggoRepository: DoggoRepository,
        schedulerProvider: SchedulerProvider
    ): SubBreedViewModel = SubBreedViewModelImpl(
        doggoRepository,
        schedulerProvider
    )
}