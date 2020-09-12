package com.eoa.tech.core.util.ext

import android.content.Context
import android.view.inputmethod.InputMethodManager


/**
 * Created by Anggit Prayogo on 06,August,2020
 * GitHub : https://github.com/anggit97
 */
fun showSoftKeyboard(context: Context) {
    val imm =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.let {
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }
}

fun hideSoftKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.let {
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}