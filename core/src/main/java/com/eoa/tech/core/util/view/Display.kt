package com.eoa.tech.core.util.view

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import com.eoa.tech.core.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


/**
 * Created by Anggit Prayogo on 10,August,2020
 * GitHub : https://github.com/anggit97
 */
object Display {

    private fun getScreenHeight(context: Context?): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)?.windowManager?.defaultDisplay
            ?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun setupFullHeight(bottomSheetDialog: BottomSheetDialog, context: Context?) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
        bottomSheet?.let {
            val layoutParams = bottomSheet.layoutParams
            val windowHeight = getScreenHeight(context)
            if (layoutParams != null) {
                layoutParams.height = windowHeight
            }
            bottomSheet.layoutParams = layoutParams
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}