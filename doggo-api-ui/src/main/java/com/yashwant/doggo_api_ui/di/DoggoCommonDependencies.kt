package com.yashwant.doggo_api_ui.di

import com.yashwant.doggo_api_bridge.imageloader.ImageLoader
import com.yashwant.doggo_api_bridge.repository.DoggoRepository
import com.yashwant.doggo_api_bridge.scheduler.SchedulerProvider

interface DoggoCommonDependencies {
    fun provideDoggoAPIRepository(): DoggoRepository
    fun schedulerProvider(): SchedulerProvider
    fun provideImageLoader(): ImageLoader
}

interface DoggoDependenciesProvider {
    fun doggoCommonDependencies(): DoggoCommonDependencies
}
