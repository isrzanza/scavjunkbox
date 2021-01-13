package com.dorcohen.scavjunkbox.data.model

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_table")
data class AppInfo(
    @PrimaryKey
    var packageName: String,
    var name: String,
    var active: Boolean = true
)

fun AppInfo.getAppIcon(application: Application): Drawable? = application.packageManager.getApplicationIcon(this.packageName)