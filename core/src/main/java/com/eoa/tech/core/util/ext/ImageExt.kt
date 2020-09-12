package com.eoa.tech.core.util.ext

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade


/**
 * Created by Anggit Prayogo on 24,July,2020
 * GitHub : https://github.com/anggit97
 */
fun ImageView.load(imageSrc: Any, radius: Int = 16) {
    Glide.with(context.applicationContext)
        .load(imageSrc)
        .transition(withCrossFade(getDrawableFactory()))
        .apply(RequestOptions().transform(RoundedCorners(radius)))
        .into(this)
}

fun ImageView.load(imageSrc: Bitmap, radius: Int = 16) {
    Glide.with(context.applicationContext)
        .load(imageSrc)
        .transition(withCrossFade(getDrawableFactory()))
        .apply(RequestOptions().transform(RoundedCorners(radius)))
        .into(this)
}

fun ImageView.loadCircle(imageSrc: Any) {
    Glide.with(context.applicationContext)
        .load(imageSrc)
        .transition(withCrossFade(getDrawableFactory()))
        .apply(
            RequestOptions()
                .transform(CircleCrop())
        )
        .into(this)
}

private fun getDrawableFactory(): DrawableCrossFadeFactory {
    return DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
}