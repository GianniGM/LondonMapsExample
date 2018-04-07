package com.giangraziano.citymapperandroidcc.common

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import java.util.concurrent.TimeUnit

/**
 * Created by giannig on 03/04/18.
 */

fun Long.converToTimeString(): String {
    val minutes = TimeUnit.SECONDS.toMinutes(this)
    val seconds = this - TimeUnit.MINUTES.toSeconds(minutes)
    return "$minutes: $seconds"
}

fun ImageView.setFromResources(filePath: String) {
    val ctx = this.context
    val stream = ctx.assets.open(filePath)
    val drawable = Drawable.createFromStream(stream, null)
    setImageDrawable(drawable)
}