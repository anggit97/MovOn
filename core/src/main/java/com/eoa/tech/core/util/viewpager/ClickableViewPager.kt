package com.eoa.tech.core.util.viewpager

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager


/**
 * Created by Anggit Prayogo on 25,August,2020
 * GitHub : https://github.com/anggit97
 */
class ClickableViewPager : ViewPager {
    private var mOnItemClickListener: OnItemClickListener? = null

    constructor(context: Context) : super(context) {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setup() {
        val tapGestureDetector =
            GestureDetector(context, TapGestureListener())
        setOnTouchListener { _, event ->
            tapGestureDetector.onTouchEvent(event)
            false
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private inner class TapGestureListener : SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(currentItem)
            }
            return true
        }
    }
}