package com.yashwant.doggo_api_ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.whenever
import com.yashwant.doggo_api_bridge.repository.DoggoRepository
import com.yashwant.doggo_api_bridge.scheduler.SchedulerProvider
import com.yashwant.doggo_api_bridge.scheduler.TestSchedulerProvider
import com.yashwant.doggo_api_bridge.state.SubBreedState
import com.yashwant.doggo_api_ui.view.subbreed.SubBreedViewModel
import com.yashwant.doggo_api_ui.view.subbreed.SubBreedViewModelImpl
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SubBreedViewModelTest {
    @Mock
    lateinit var doggoRepository: DoggoRepository
    private val schedulerProvider: SchedulerProvider = TestSchedulerProvider()
    private lateinit var subBreedViewModel: SubBreedViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        subBreedViewModel = SubBreedViewModelImpl(doggoRepository, schedulerProvider)
    }

    @Test
    fun testBind() {
        val list = ArrayList<String>()
        list.add("url1")
        list.add("url2")
        whenever(doggoRepository.getSubBreedList("test")).thenReturn(
            Observable.just(
                SubBreedState.DataState(
                    list
                )
            )
        )
        subBreedViewModel.bind("test")
        Assert.assertEquals(SubBreedState.DataState(list), subBreedViewModel.getData().value)
    }

    @Test
    fun testObserveDoggoListReturnsError() {
        whenever(doggoRepository.getSubBreedList("test")).thenReturn(Observable.error(Throwable("Something")))
        subBreedViewModel.bind("test")
        Assert.assertEquals(
            SubBreedState.ErrorState("Something"),
            subBreedViewModel.getData().value
        )
    }
}