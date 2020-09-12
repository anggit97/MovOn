package com.eoa.tech.core.util.number

import java.math.RoundingMode
import java.text.DecimalFormat


/**
 * Created by Anggit Prayogo on 01,September,2020
 * GitHub : https://github.com/anggit97
 */
object NumberConverter {

    fun roundDecimal(result: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(result).toDouble()
    }

    fun thousandSeparator(number: Long): String {
        return String.format("%,d", number)
    }

    fun thousandSeparator(number: String): String {
        return String.format("%,d", number.toLong())
    }
}