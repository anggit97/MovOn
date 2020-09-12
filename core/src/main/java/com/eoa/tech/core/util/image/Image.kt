package com.eoa.tech.core.util.image

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat


/**
 * Created by Anggit Prayogo on 05,August,2020
 * GitHub : https://github.com/anggit97
 */
fun ImageView.setColor(color: Int) {
    val drawable = this.drawable
    drawable.colorFilter =
        BlendModeColorFilter(ContextCompat.getColor(context, color), BlendMode.SRC_ATOP)
}

fun TextView.setColor(color: Int) {
    val drawable = this.background
    drawable.colorFilter =
        BlendModeColorFilter(ContextCompat.getColor(context, color), BlendMode.SRC_ATOP)
}