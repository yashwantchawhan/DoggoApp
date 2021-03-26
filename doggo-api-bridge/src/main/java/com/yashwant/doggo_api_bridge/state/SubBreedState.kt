package com.yashwant.doggo_api_bridge.state


sealed class SubBreedState {
    object LoadingState : SubBreedState()
    data class DataState(val data: List<String>) : SubBreedState()
    data class ErrorState(val data: String) : SubBreedState()
}