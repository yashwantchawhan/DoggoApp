package com.yashwant.doggo_api_ui.view.subbreeddetail

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.yashwant.doggo_api_bridge.state.DoggoState
import com.yashwant.doggo_api_ui.R

class BreedDetailFragment : Fragment(R.layout.fragment_detail_breed) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView : AppCompatImageView = view.findViewById(R.id.imageView)
        val url = arguments?.getString("url")
        Glide.with(imageView)
            .load(url) //3
            .centerCrop() //4
            .placeholder(R.drawable.ic_doggo)
            .error(R.drawable.ic_doggo)
            .fallback(R.drawable.ic_doggo)
            .into(imageView)
    }
}