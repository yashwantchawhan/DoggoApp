package com.yashwant.doggo_api_bridge.api

import com.yashwant.doggo_api_bridge.models.DoggoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface DoggoAPI {
    @GET("breeds/list")
    fun getAllBreedsList(): Observable<DoggoResponse>

    @GET("breed/{name}/images")
    fun getSubBreedList(@Path("name") name: String): Observable<DoggoResponse>
}