package com.yashwant.doggo_api_ui.di

import android.app.Activity
import com.yashwant.doggo_api_bridge.repository.DoggoRepository
import com.yashwant.doggo_api_bridge.scheduler.SchedulerProvider
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
            doggoCommonDependencies: DoggoCommonDependencies,
            @BindsInstance activity: Activity,
        ): BreedListComponent
    }

    fun inject(fragment: BreedListFragment)
}

@Module
object BreedListModule {

    @Provides
    fun breedListViewModel(
        doggoRepository: DoggoRepository,
        schedulerProvider: SchedulerProvider
    ): BreedListViewModel = BreedListViewModelImpl(
        doggoRepository,
        schedulerProvider
    )
}