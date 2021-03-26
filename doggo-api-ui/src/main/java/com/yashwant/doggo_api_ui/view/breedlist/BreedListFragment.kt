package com.yashwant.doggo_api_ui.view.breedlist

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yashwant.doggo_api_bridge.state.DoggoState
import com.yashwant.doggo_api_ui.R
import com.yashwant.doggo_api_ui.di.DaggerBreedListComponent
import com.yashwant.doggo_api_ui.di.DoggoDependenciesProvider
import javax.inject.Inject

class BreedListFragment : Fragment(R.layout.fragment_list_breed) {

    @Inject
    lateinit var breedListViewModel: BreedListViewModel
    lateinit var breedListAdapter: BreedListAdapter
    lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.breedList)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        breedListAdapter = BreedListAdapter()
        recyclerView.adapter = breedListAdapter
        breedListViewModel.getData().observe(viewLifecycleOwner, {
            when (it) {
                is DoggoState.LoadingState -> renderLoadingState()
                is DoggoState.DataState -> renderDataState(it)
                is DoggoState.ErrorState -> renderErrorState(it)
            }
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpDI()
        breedListViewModel.bind()
    }

    private fun setUpDI() {
        activity?.let {
            DaggerBreedListComponent.factory()
                .create(dependencies(it), it)
                .inject(this)
        }
    }

    override fun onStop() {
        super.onStop()
        breedListViewModel.unbind()
    }

    private fun dependencies(activity: Activity) =
        (activity.application as DoggoDependenciesProvider).doggoCommonDependencies()

    private fun renderLoadingState() {
        Log.d("MainActivity", "Loading")
    }

    private fun renderDataState(dataState: DoggoState.DataState) {
        breedListAdapter.setDoggoList(dataState.data)
        Log.d("MainActivity", dataState.data[0])
    }
}

private fun renderErrorState(dataState: DoggoState.ErrorState) {
    Log.d("MainActivity", dataState.data)
}




