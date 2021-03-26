package com.yashwant.doggo_api_ui.view

import androidx.lifecycle.MutableLiveData
import com.yashwant.doggo_api_bridge.repository.DoggoRepository
import com.yashwant.doggo_api_bridge.state.DoggoState
import com.yashwant.doggo_api_bridge.state.SubBreedState
import com.yashwant.doggo_api_ui.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

interface SubBreedViewModel {
    fun bind(breedName: String)
    fun unbind()
    fun getData(): MutableLiveData<SubBreedState>
}

class SubBreedViewModelImpl(
    private val doggoRepository: DoggoRepository,
    private val schedulerProvider: SchedulerProvider
) : SubBreedViewModel {
    private val compositeDisposable = CompositeDisposable()
    private val state = MutableLiveData<SubBreedState>()

    override fun bind(breedName: String) {
        compositeDisposable.add(observeSubBreedList(breedName))
    }

    override fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    override fun getData(): MutableLiveData<SubBreedState> {
        return state
    }

    private fun observeSubBreedList(breedName: String) =
        doggoRepository.getSubBreedList(breedName)
            .subscribeOn(schedulerProvider.ui())
            .doOnSubscribe {
                state.postValue(SubBreedState.LoadingState)
            }
            .subscribeBy(onError = {
                state.postValue(SubBreedState.ErrorState(it.localizedMessage))
            }) {
                state.postValue(it)
            }

}
