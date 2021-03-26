package com.yashwant.doggo_api_ui.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yashwant.doggo_api_bridge.repository.DoggoRepository
import com.yashwant.doggo_api_bridge.state.DoggoState
import com.yashwant.doggo_api_ui.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

interface BreedListViewModel {
    fun bind()
    fun unbind()
    fun getData(): MutableLiveData<DoggoState>
}

class BreedListViewModelImpl @Inject constructor(
    private val doggoRepository: DoggoRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel(), BreedListViewModel {
    private val compositeDisposable = CompositeDisposable()
    private val state = MutableLiveData<DoggoState>()

    override fun bind() {
        compositeDisposable.add(observeDoggoList())
    }

    override fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    override fun getData(): MutableLiveData<DoggoState> {
        return state
    }

    private fun observeDoggoList() = doggoRepository
        .getAllBreedsList()
        .subscribeOn(schedulerProvider.ui())
        .doOnSubscribe {
            state.postValue(DoggoState.LoadingState)
        }
        .subscribeBy(onError = {
            state.postValue(DoggoState.ErrorState(it.localizedMessage))
        }) {
            state.postValue(it)
        }
}