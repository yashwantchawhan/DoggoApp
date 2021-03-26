package com.yashwant.doggo_api_ui.view.subbreed

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.yashwant.doggo_api_bridge.state.SubBreedState
import com.yashwant.doggo_api_ui.R
import com.yashwant.doggo_api_ui.di.DaggerSubBreedComponent
import com.yashwant.doggo_api_ui.di.DoggoDependenciesProvider
import javax.inject.Inject

class SubBreedFragment : Fragment(R.layout.fragment_list_sub_breed) {
    @Inject
    lateinit var subBreedViewModel: SubBreedViewModel
    private lateinit var subBreedListAdapter: SubBreedListAdapter
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var subBreedErrorView: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subBreedListAdapter = SubBreedListAdapter()
        recyclerView = view.findViewById(R.id.subBreedList)
        subBreedErrorView = view.findViewById(R.id.subBreedErrorView)
        shimmerFrameLayout = view.findViewById(R.id.shimmerLayoutSubBreed)
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
        shimmerFrameLayout.visibility = View.VISIBLE
        shimmerFrameLayout.startShimmer()
    }

    private fun renderDataState(dataState: SubBreedState.DataState) {
        shimmerFrameLayout.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        shimmerFrameLayout.stopShimmer()
        subBreedListAdapter.submitList(dataState.data)
    }

    private fun renderErrorState(dataState: SubBreedState.ErrorState) {
        shimmerFrameLayout.visibility = View.GONE
        shimmerFrameLayout.stopShimmer()
        subBreedErrorView.text = dataState.data
        subBreedErrorView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
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