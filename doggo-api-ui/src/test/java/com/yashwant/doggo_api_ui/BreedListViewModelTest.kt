package com.yashwant.doggo_api_ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.whenever
import com.yashwant.doggo_api_bridge.repository.DoggoRepository
import com.yashwant.doggo_api_bridge.state.DoggoState
import com.yashwant.doggo_api_ui.scheduler.SchedulerProvider
import com.yashwant.doggo_api_ui.scheduler.TestSchedulerProvider
import com.yashwant.doggo_api_ui.view.BreedListViewModel
import com.yashwant.doggo_api_ui.view.BreedListViewModelImpl
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BreedListViewModelTest {
    @Mock
    lateinit var doggoRepository: DoggoRepository
    private val schedulerProvider: SchedulerProvider = TestSchedulerProvider()
    private lateinit var breedListViewModel: BreedListViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        breedListViewModel = BreedListViewModelImpl(doggoRepository, schedulerProvider)
    }

    @Test
    fun testBind() {
        val list = ArrayList<String>()
        list.add("first")
        list.add("second")
        whenever(doggoRepository.getAllBreedsList()).thenReturn(
            Observable.just(
                DoggoState.DataState(
                    list
                )
            )
        )
        breedListViewModel.bind()
        Assert.assertEquals(DoggoState.DataState(list), breedListViewModel.getData().value)
    }

    @Test
    fun testObserveDoggoListReturnsError() {
        whenever(doggoRepository.getAllBreedsList()).thenReturn(Observable.error(Throwable("Something")))
        breedListViewModel.bind()
        Assert.assertEquals(DoggoState.ErrorState("Something"), breedListViewModel.getData().value)
    }
}