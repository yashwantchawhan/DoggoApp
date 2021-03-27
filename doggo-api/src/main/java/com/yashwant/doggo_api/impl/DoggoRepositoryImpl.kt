package com.yashwant.doggo_api.impl

import com.yashwant.doggo_api_bridge.api.DoggoAPI
import com.yashwant.doggo_api_bridge.repository.DoggoRepository
import com.yashwant.doggo_api_bridge.scheduler.SchedulerProvider
import com.yashwant.doggo_api_bridge.state.DoggoState
import com.yashwant.doggo_api_bridge.state.SubBreedState
import io.reactivex.Observable

class DoggoRepositoryImpl(
    private val doggoAPI: DoggoAPI,
    private val schedulerProvider: SchedulerProvider
) : DoggoRepository {
    override fun getAllBreedsList(): Observable<DoggoState> =
        doggoAPI.getAllBreedsList()
            .subscribeOn(schedulerProvider.io())
            .map<DoggoState> { doggoResponse ->
                doggoResponse.message.let {
                    DoggoState.DataState(
                        it
                    )
                }
            }
            .onErrorReturn { DoggoState.ErrorState(it.localizedMessage) }

    override fun getSubBreedList(breedName: String): Observable<SubBreedState> {
        return doggoAPI.getSubBreedList(breedName)
            .subscribeOn(schedulerProvider.io())
            .map<SubBreedState> { doggoResponse ->
                doggoResponse.message.let {
                    SubBreedState.DataState(
                        it
                    )
                }
            }
            .onErrorReturn { SubBreedState.ErrorState(it.localizedMessage) }
    }
}