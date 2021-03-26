package com.yashwant.doggo_api_bridge.state

sealed class DoggoState {
    object LoadingState : DoggoState()
    data class DataState(val data: List<String>) : DoggoState()
    data class ErrorState(val data: String) : DoggoState()
}