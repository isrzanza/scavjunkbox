package com.dorcohen.scavjunkbox.data.repository

import androidx.lifecycle.LiveData
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.data.model.ScavLine
import com.dorcohen.scavjunkbox.util.scav.IScavHelper
import com.dorcohen.scavjunkbox.util.system.ISystemUtil

interface IRepository {
    val appList: LiveData<List<AppInfo>>
    val scavLines: List<ScavLine>
    val systemUtil: ISystemUtil
    suspend fun addNewApp(appInfo:AppInfo)
    suspend fun toggleApp(appInfo:AppInfo)
    suspend fun getAppsBlocking():List<AppInfo>
}
