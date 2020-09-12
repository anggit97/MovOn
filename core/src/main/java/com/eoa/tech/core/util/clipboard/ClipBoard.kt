package com.eoa.tech.core.util.clipboard

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context


/**
 * Created by Anggit Prayogo on 11,September,2020
 * GitHub : https://github.com/anggit97
 */
object ClipBoard {

    fun copyClipBoard(activity: Activity, description: String) {
        val myClipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val myClip: ClipData = ClipData.newPlainText("desc", description)
        myClipboard.setPrimaryClip(myClip)
    }
}