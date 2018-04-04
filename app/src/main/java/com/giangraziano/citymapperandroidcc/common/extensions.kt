package com.giangraziano.citymapperandroidcc.common

import java.util.concurrent.TimeUnit

/**
 * Created by giannig on 03/04/18.
 */

fun Long.converToTimeString() : String {
    val minutes = TimeUnit.SECONDS.toMinutes(this)
    val seconds = this - TimeUnit.MINUTES.toSeconds(minutes)
    return "$minutes: $seconds"
}