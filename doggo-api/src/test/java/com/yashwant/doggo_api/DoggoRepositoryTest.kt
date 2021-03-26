package com.yashwant.doggo_api

import com.nhaarman.mockito_kotlin.whenever
import com.yashwant.doggo_api.impl.DoggoRepositoryImpl
import com.yashwant.doggo_api_bridge.api.DoggoAPI
import com.yashwant.doggo_api_bridge.models.DoggoResponse
import com.yashwant.doggo_api_bridge.repository.DoggoRepository
import com.yashwant.doggo_api_bridge.scheduler.TestSchedulerProvider
import com.yashwant.doggo_api_bridge.state.DoggoState
import com.yashwant.doggo_api_bridge.state.SubBreedState
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DoggoRepositoryTest {

    @Mock
    lateinit var doggoAPI: DoggoAPI

    private lateinit var doggoRepository: DoggoRepository
    private val testSchedulers = TestSchedulerProvider()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        doggoRepository = DoggoRepositoryImpl(doggoAPI, testSchedulers)
    }

    @Test
    fun testGetAllBreedsList() {
        val list = ArrayList<String>()
        list.add("first")
        list.add("second")
        whenever(doggoAPI.getAllBreedsList()).thenReturn(
            Observable.just(
                DoggoResponse(
                    list,
                    "202"
                )
            )
        )
        doggoRepository
            .getAllBreedsList()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue {
                it == DoggoState.DataState(list)
            }
            .dispose()
    }

    @Test
    fun testGetAllBreedsListError() {
        whenever(doggoAPI.getAllBreedsList()).thenReturn(
            Observable.error(Throwable("Error"))
        )
        doggoRepository
            .getAllBreedsList()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue {
                it == DoggoState.ErrorState("Error")
            }
            .dispose()
    }

    @Test
    fun testGetSubBreedsList() {
        val list = ArrayList<String>()
        list.add("url1")
        list.add("url2")
        whenever(doggoAPI.getSubBreedList("test")).thenReturn(
            Observable.just(
                DoggoResponse(
                    list,
                    "202"
                )
            )
        )
        doggoRepository
            .getSubBreedList("test")
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue {
                it == SubBreedState.DataState(list)
            }
            .dispose()
    }

    @Test
    fun testGetSubBreedsListError() {
        whenever(doggoAPI.getSubBreedList("test")).thenReturn(
            Observable.error(Throwable("Error"))
        )
        doggoRepository
            .getSubBreedList("test")
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue {
                it == SubBreedState.ErrorState("Error")
            }
            .dispose()
    }
}