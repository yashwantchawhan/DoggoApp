package com.yashwant.doggo_api_ui.view.subbreeddetail

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.yashwant.doggo_api_bridge.imageloader.ImageLoader
import com.yashwant.doggo_api_ui.R
import com.yashwant.doggo_api_ui.di.DaggerBreedDetailComponent
import com.yashwant.doggo_api_ui.di.DoggoDependenciesProvider
import com.yashwant.doggo_api_ui.view.KEY_URL
import javax.inject.Inject

class BreedDetailFragment : Fragment(R.layout.fragment_detail_breed) {
    @Inject
    lateinit var imageLoader: ImageLoader
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDI()
        val imageView: AppCompatImageView = view.findViewById(R.id.imageView)
        val url = arguments?.getString(KEY_URL)
        url?.let { imageLoader.loadImage(imageView, it, R.drawable.ic_doggo) }
    }

    private fun setUpDI() {
        requireActivity().let {
            DaggerBreedDetailComponent.factory()
                .create(dependencies(it), it)
                .inject(this)
        }
    }

    private fun dependencies(activity: Activity) =
        (activity.application as DoggoDependenciesProvider).doggoCommonDependencies()
}