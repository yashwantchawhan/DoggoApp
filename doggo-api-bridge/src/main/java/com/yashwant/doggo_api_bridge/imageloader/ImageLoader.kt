package com.yashwant.doggo_api_bridge.imageloader

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView

interface ImageLoader {
   fun loadImage(image:AppCompatImageView,url: String, @DrawableRes id: Int)
}