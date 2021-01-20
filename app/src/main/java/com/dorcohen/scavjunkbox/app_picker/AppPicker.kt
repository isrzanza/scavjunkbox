package com.dorcohen.scavjunkbox.app_picker

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.dorcohen.scavjunkbox.data.model.AppInfo

object AppPicker : IAppPicker {
    override suspend fun getInstalledAppList(application:Application):List<AppInfo>{
        val res = ArrayList<AppInfo>()
        val pm = application.packageManager
        val appList = pm.getInstalledApplications(PackageManager.GET_META_DATA or PackageManager.GET_SHARED_LIBRARY_FILES)
        appList.forEach {
           if((it.flags and ApplicationInfo.FLAG_SYSTEM) != 1){
               val appName = pm.getApplicationLabel(it).toString()
               //val icon = pm.getApplicationIcon(it)
               val ai = AppInfo(it.packageName,appName)
               res.add(ai)
           }
        }
        return res.sortedBy { ai -> ai.name }
    }
}