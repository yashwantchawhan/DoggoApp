package com.yashwant.doggo_api_ui.view.subbreed

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.yashwant.doggo_api_bridge.imageloader.ImageLoader
import com.yashwant.doggo_api_bridge.state.SubBreedState
import com.yashwant.doggo_api_ui.R
import com.yashwant.doggo_api_ui.di.DaggerSubBreedComponent
import com.yashwant.doggo_api_ui.di.DoggoDependenciesProvider
import com.yashwant.doggo_api_ui.view.KEY_BREED_NAME
import com.yashwant.doggo_api_ui.view.KEY_URL
import javax.inject.Inject

class SubBreedFragment : Fragment(R.layout.fragment_list_sub_breed), SubBreedListItemClickListener {
    @Inject
    lateinit var subBreedViewModel: SubBreedViewModel
    @Inject
    lateinit var imageLoader: ImageLoader
    private lateinit var subBreedListAdapter: SubBreedListAdapter
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var subBreedErrorView: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subBreedListAdapter = SubBreedListAdapter(imageLoader)
        subBreedListAdapter.setOnSubBreedListItemClickListener(this)
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
        val breedName = arguments?.getString(KEY_BREED_NAME)
        breedName?.let { subBreedViewModel.bind(it) }
    }

    private fun setUpDI() {
        requireActivity().let {
            DaggerSubBreedComponent.factory()
                .create(dependencies(it), it)
                .inject(this)
        }
    }

    private fun dependencies(activity: Activity) =
        (activity.application as DoggoDependenciesProvider).doggoCommonDependencies()

    override fun onSubBreedItemClick(url: String) {
        val bundle = Bundle().apply {
            putString(KEY_URL,url)
        }
        findNavController().navigate(
            R.id.action_subBreedFragment_to_breedDetailFragment,
            bundle,
            navOptions {
                anim {
                    enter = android.R.animator.fade_in
                    exit = android.R.animator.fade_out
                }
            }
        )
    }

}