package com.dorcohen.scavjunkbox.app_picker

import android.app.Application
import com.dorcohen.scavjunkbox.data.model.AppInfo

interface IAppPicker {
    suspend fun getInstalledAppList(application: Application): List<AppInfo>
}