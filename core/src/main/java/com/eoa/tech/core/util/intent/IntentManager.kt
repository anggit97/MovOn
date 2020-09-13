package com.eoa.tech.core.util.intent

import android.content.Intent


/**
 * Created by Anggit Prayogo on 13,September,2020
 * GitHub : https://github.com/anggit97
 */
object IntentManager {

    fun createShareIntent(content: String): Intent {
        return Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_TEXT, content)
        }
    }
}