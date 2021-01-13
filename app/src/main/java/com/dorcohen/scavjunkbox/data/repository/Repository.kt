package com.dorcohen.scavjunkbox.data.repository

import androidx.lifecycle.LiveData
import com.dorcohen.scavjunkbox.data.database.Database
import com.dorcohen.scavjunkbox.data.model.AppInfo


class Repository(database: Database) : IRepository {
    private val mainDao = database.mainDao()

    override val appList: LiveData<List<AppInfo>>
        get() = mainDao.getApps()

    override suspend fun addNewApp(appInfo: AppInfo) = mainDao.addApp(appInfo)

    override suspend fun toggleApp(appInfo:AppInfo)  = mainDao.toggleApp(appInfo.copy(active = !appInfo.active))

    override suspend fun getAppsBlocking(): List<AppInfo> = mainDao.getAppsBlocking()
}