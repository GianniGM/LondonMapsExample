package com.giangraziano.citymapperandroidcc.extensions

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
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


fun AppCompatActivity.isPermissionGranted(permission: String) =
        ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun AppCompatActivity.shouldShowPermissionRationale(permission: String) =
        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun AppCompatActivity.requestPermission(permission: String, requestId: Int) =
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestId)

fun AppCompatActivity.batchRequestPermissions(permissions: Array<String>, requestId: Int) =
        ActivityCompat.requestPermissions(this, permissions, requestId)