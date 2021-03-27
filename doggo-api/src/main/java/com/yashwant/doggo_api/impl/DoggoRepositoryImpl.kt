package com.yashwant.doggo_api.impl

import com.yashwant.doggo_api_bridge.api.DoggoAPI
import com.yashwant.doggo_api_bridge.repository.DoggoRepository
import com.yashwant.doggo_api_bridge.state.DoggoState
import com.yashwant.doggo_api_bridge.state.SubBreedState
import io.reactivex.Observable

class DoggoRepositoryImpl(
    private val doggoAPI: DoggoAPI,
) : DoggoRepository {
    override fun getAllBreedsList(): Observable<DoggoState> =
        doggoAPI.getAllBreedsList()
            .map<DoggoState> { doggoResponse ->
                doggoResponse.message.let {
                    DoggoState.DataState(
                        it)
                }
            }
            .onErrorReturn { DoggoState.ErrorState(it.localizedMessage) }

    override fun getSubBreedList(breedName: String): Observable<SubBreedState> {
        return doggoAPI.getSubBreedList(breedName)
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