package com.dorcohen.scavjunkbox.data.repository

import androidx.lifecycle.LiveData
import com.dorcohen.scavjunkbox.data.model.AppInfo

interface IRepository {
    val appList: LiveData<List<AppInfo>>
    suspend fun addNewApp(appInfo:AppInfo)
    suspend fun toggleApp(appInfo:AppInfo)
    suspend fun getAppsBlocking():List<AppInfo>
}
