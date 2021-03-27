package com.yashwant.doggo_api_ui.view.breedlist

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.yashwant.doggo_api_bridge.state.DoggoState
import com.yashwant.doggo_api_ui.R
import com.yashwant.doggo_api_ui.di.DaggerBreedListComponent
import com.yashwant.doggo_api_ui.di.DoggoDependenciesProvider
import com.yashwant.doggo_api_ui.view.KEY_BREED_NAME
import javax.inject.Inject

class BreedListFragment : Fragment(R.layout.fragment_list_breed), ListItemClickListener {

    @Inject
    lateinit var breedListViewModel: BreedListViewModel
    private lateinit var breedListAdapter: BreedListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var errorTextView: TextView
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.breedList)
        errorTextView = view.findViewById(R.id.errorTextView)
        shimmerFrameLayout = view.findViewById(R.id.shimmerLayout);
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        breedListAdapter = BreedListAdapter()
        breedListAdapter.setOnListItemClickListener(this)
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
        requireActivity().let {
            DaggerBreedListComponent.factory()
                .create(dependencies(it), it)
                .inject(this)
        }
    }

    override fun onStop() {
        super.onStop()
    }

    private fun dependencies(activity: Activity) =
        (activity.application as DoggoDependenciesProvider).doggoCommonDependencies()

    private fun renderLoadingState() {
        shimmerFrameLayout.visibility = View.VISIBLE
        shimmerFrameLayout.startShimmer();
    }

    private fun renderDataState(dataState: DoggoState.DataState) {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.visibility = View.GONE
        breedListAdapter.setDoggoList(dataState.data)
    }

    private fun renderErrorState(dataState: DoggoState.ErrorState) {
        shimmerFrameLayout.visibility = View.GONE
        shimmerFrameLayout.stopShimmer()
        recyclerView.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = dataState.data
    }

    override fun onClick(breedName: String) {
        val bundle = Bundle().also {
            it.putString(KEY_BREED_NAME, breedName)
        }
        findNavController().navigate(
            R.id.action_breedListFragment_to_subBreedFragment,
            bundle,
            navOptions { // Use the Kotlin DSL for building NavOptions
                anim {
                    enter = android.R.animator.fade_in
                    exit = android.R.animator.fade_out
                }
            }
        )
    }
}






