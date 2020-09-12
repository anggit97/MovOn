package com.eoa.tech.core.util.date

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Anggit Prayogo on 31,August,2020
 * GitHub : https://github.com/anggit97
 */
object DateUtils {

    fun convertTimeDynamic(
        time: String?,
        currentTimeFormatActual: String?,
        targetTimeFormatActual: String?
    ): String? {
        val currentTime: Date?
        val outputTime: String?
        return try {
            @SuppressLint("SimpleDateFormat") val currentFormat =
                SimpleDateFormat(currentTimeFormatActual)
            @SuppressLint("SimpleDateFormat") val format =
                SimpleDateFormat(targetTimeFormatActual)
            currentTime = currentFormat.parse(time ?: "")
            outputTime = format.format(currentTime ?: "")
            outputTime
        } catch (e: ParseException) {
            e.printStackTrace()
            e.localizedMessage
        }
    }
}