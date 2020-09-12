package com.eoa.tech.core.util.video

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever


/**
 * Created by Anggit Prayogo on 28,August,2020
 * GitHub : https://github.com/anggit97
 */
fun retrieveVideoFrameFromVideo(videoPath: String?): Bitmap? {
    val bitmap: Bitmap?
    var mediaMetadataRetriever: MediaMetadataRetriever? = null
    try {
        mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(videoPath, HashMap())
        //   mediaMetadataRetriever.setDataSource(videoPath);
        bitmap = mediaMetadataRetriever.frameAtTime
    } catch (e: Exception) {
        e.printStackTrace()
        throw Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.message)
    } finally {
        mediaMetadataRetriever?.release()
    }
    return bitmap
}