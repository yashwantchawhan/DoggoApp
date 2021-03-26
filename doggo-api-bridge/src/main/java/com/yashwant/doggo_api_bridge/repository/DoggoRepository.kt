package com.yashwant.doggo_api_bridge.repository

import com.yashwant.doggo_api_bridge.state.DoggoState
import com.yashwant.doggo_api_bridge.state.SubBreedState
import io.reactivex.Observable

interface DoggoRepository {
    fun getAllBreedsList(): Observable<DoggoState>

    fun getSubBreedList(breedName: String): Observable<SubBreedState>
}
