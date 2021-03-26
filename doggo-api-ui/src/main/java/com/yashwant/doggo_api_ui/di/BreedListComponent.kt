package com.yashwant.doggo_api_ui.di

import android.app.Activity
import com.yashwant.doggo_api_bridge.repository.DoggoRepository
import com.yashwant.doggo_api_ui.scheduler.SchedulerProvider
import com.yashwant.doggo_api_ui.view.BreedListViewModel
import com.yashwant.doggo_api_ui.view.BreedListViewModelImpl
import com.yashwant.doggo_api_ui.view.breedlist.BreedListFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
    modules = [BreedListModule::class],
    dependencies = [DoggoCommonDependencies::class]
)
interface BreedListComponent {
    @Component.Factory
    interface Factory {
        fun create(
            starWarsCommonDependencies: DoggoCommonDependencies,
            @BindsInstance activity: Activity,
        ): BreedListComponent
    }

    fun inject(fragment: BreedListFragment)
}

@Module
object BreedListModule {

    @Provides
    @JvmStatic
    fun breedListViewModel(
        doggoRepository: DoggoRepository,
        schedulerProvider: SchedulerProvider
    ): BreedListViewModel = BreedListViewModelImpl(
        doggoRepository,
        schedulerProvider
    )


    @Provides
    @JvmStatic
    fun schedularProvider(): SchedulerProvider = SchedulerProvider()
}