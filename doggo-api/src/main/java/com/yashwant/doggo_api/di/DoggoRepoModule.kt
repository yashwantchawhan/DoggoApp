package com.yashwant.doggo_api.di

import com.yashwant.doggo_api.impl.DoggoRepositoryImpl
import com.yashwant.doggo_api_bridge.api.DoggoAPI
import com.yashwant.doggo_api_bridge.repository.DoggoRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object DoggoRepoModule {
    @Provides
    fun providesDoggoAPIApi(retrofit: Retrofit): DoggoAPI = retrofit.create(DoggoAPI::class.java)

    @Provides
    fun provideDoggoAPIRepository(
        doggoAPI: DoggoAPI
    ): DoggoRepository = DoggoRepositoryImpl(
        doggoAPI
    )
}