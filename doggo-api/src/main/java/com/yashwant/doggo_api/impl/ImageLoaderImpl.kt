package com.yashwant.doggo_api.impl

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.yashwant.doggo_api_bridge.imageloader.ImageLoader

class ImageLoaderImpl : ImageLoader {
    override fun loadImage(image: AppCompatImageView, url: String, @DrawableRes id: Int) {
        Glide.with(image)
            .load(url)
            .centerCrop()
            .placeholder(id)
            .error(id)
            .fallback(id)
            .into(image)
    }
}