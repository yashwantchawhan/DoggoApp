package com.yashwant.doggo_api_ui.view.subbreed

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yashwant.doggo_api_bridge.state.SubBreedState
import com.yashwant.doggo_api_ui.R
import com.yashwant.doggo_api_ui.di.DaggerSubBreedComponent
import com.yashwant.doggo_api_ui.di.DoggoDependenciesProvider
import com.yashwant.doggo_api_ui.view.SubBreedViewModel
import javax.inject.Inject

class SubBreedFragment : Fragment(R.layout.fragment_list_sub_breed) {
    @Inject
    lateinit var subBreedViewModel: SubBreedViewModel
    private lateinit var subBreedListAdapter: SubBreedListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.subBreedList)
        subBreedListAdapter = SubBreedListAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = subBreedListAdapter

        subBreedViewModel.getData().observe(viewLifecycleOwner, {
            when (it) {
                is SubBreedState.LoadingState -> renderLoadingState()
                is SubBreedState.DataState -> renderDataState(it)
                is SubBreedState.ErrorState -> renderErrorState(it)
            }
        })
    }

    private fun renderLoadingState() {
    }

    private fun renderDataState(dataState: SubBreedState.DataState) {
        subBreedListAdapter.submitList(dataState.data)
    }

    private fun renderErrorState(dataState: SubBreedState.ErrorState) {
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpDI()
        val breedName = arguments?.getString("breedName")
        breedName?.let { subBreedViewModel.bind(it) }
    }

    override fun onStop() {
        super.onStop()
        subBreedViewModel.unbind()
    }

    private fun setUpDI() {
        activity?.let {
            DaggerSubBreedComponent.factory()
                .create(dependencies(it), it)
                .inject(this)
        }
    }

    private fun dependencies(activity: Activity) =
        (activity.application as DoggoDependenciesProvider).doggoCommonDependencies()

}