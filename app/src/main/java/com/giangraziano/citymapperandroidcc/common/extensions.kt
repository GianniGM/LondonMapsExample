package com.giangraziano.citymapperandroidcc.common

import android.content.Context
import android.widget.Toast
import java.sql.Time
import java.util.concurrent.TimeUnit

/**
 * Created by giannig on 03/04/18.
 */

fun Long.converToTimeString() : String {
    val minutes = TimeUnit.SECONDS.toMinutes(this)
    val seconds = this - TimeUnit.MINUTES.toSeconds(minutes)
    return "$minutes: $seconds"
}