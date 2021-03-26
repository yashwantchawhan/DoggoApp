package com.yashwant.doggo_api_ui.di

import com.yashwant.doggo_api_bridge.repository.DoggoRepository

interface DoggoCommonDependencies {
    fun provideDoggoAPIRepository(): DoggoRepository
}

interface DoggoDependenciesProvider {
    fun doggoCommonDependencies(): DoggoCommonDependencies
}
