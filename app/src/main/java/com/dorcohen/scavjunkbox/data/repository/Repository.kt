package com.dorcohen.scavjunkbox.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dorcohen.scavjunkbox.data.database.Database
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.data.model.ScavLine
import com.dorcohen.scavjunkbox.data.model.SystemMessage
import com.dorcohen.scavjunkbox.util.scav.IScavHelper
import com.dorcohen.scavjunkbox.util.scav.ScavHelper
import com.dorcohen.scavjunkbox.util.system.ISystemUtil


class Repository(private val context: Context, database: Database) : IRepository, ISystemUtil,
    IScavHelper by ScavHelper {
    init {
        generateScavLineList(context)
    }

    private val mainDao = database.mainDao()

    private val sysMessage: MutableLiveData<SystemMessage> = MutableLiveData()

    override val appList: LiveData<List<AppInfo>>
        get() = mainDao.getApps()

    override val systemUtil: ISystemUtil = this

    override suspend fun addNewApp(appInfo: AppInfo) = mainDao.addApp(appInfo)

    override suspend fun toggleApp(appInfo:AppInfo)  = mainDao.toggleApp(appInfo.copy(active = !appInfo.active))

    override suspend fun getAppsBlocking(): List<AppInfo> = mainDao.getAppsBlocking()

    override fun getSystemMessage(): LiveData<SystemMessage> = sysMessage

    override fun setSystemMessage(message: SystemMessage) = sysMessage.postValue(message)

    override fun getString(resId: Int): String  = context.getString(resId)
}