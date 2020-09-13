package com.anggitprayogo.movon.data.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * Created by Anggit Prayogo on 13,September,2020
 * GitHub : https://github.com/anggit97
 */
@Parcelize
data class ShareEntity(
    val title: String?,
    val body: String?
) : Parcelable