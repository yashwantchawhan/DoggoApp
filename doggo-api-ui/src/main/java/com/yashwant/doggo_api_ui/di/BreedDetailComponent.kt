package com.yashwant.doggo_api_ui.di

import android.app.Activity
import com.yashwant.doggo_api_ui.view.subbreeddetail.BreedDetailFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module


@Component(
    modules = [BreedDetailModule::class],
    dependencies = [DoggoCommonDependencies::class]
)
interface BreedDetailComponent {
    @Component.Factory
    interface Factory {
        fun create(
            doggoCommonDependencies: DoggoCommonDependencies,
            @BindsInstance activity: Activity,
        ): BreedDetailComponent
    }

    fun inject(fragment: BreedDetailFragment)
}

@Module
object BreedDetailModule {}