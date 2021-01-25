package com.dorcohen.scavjunkbox.service

import android.app.KeyguardManager
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.lifecycle.Observer
import com.dorcohen.scavjunkbox.IRepoAccess
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.data.repository.IRepository
import com.dorcohen.scavjunkbox.resIdToUri
import com.dorcohen.scavjunkbox.util.audio.AudioHelper
import com.dorcohen.scavjunkbox.util.audio.IAudioHelper
import com.dorcohen.scavjunkbox.util.scav.IScavHelper
import com.dorcohen.scavjunkbox.util.scav.ScavHelper

class NotificationListener :
    NotificationListenerService(),
    IAudioHelper by AudioHelper(),
    IScavHelper by ScavHelper {

    companion object {
        private val categoryList = listOf(
            Notification.CATEGORY_MESSAGE,
            Notification.CATEGORY_EMAIL,
            Notification.CATEGORY_RECOMMENDATION,
            Notification.CATEGORY_REMINDER,
            Notification.CATEGORY_SOCIAL
        )
    }

    private var repository: IRepository? = null
    private var appList: ArrayList<AppInfo> = arrayListOf()

    override fun onCreate() {
        super.onCreate()
        try {
            val repoAccess = application as IRepoAccess
            repository = repoAccess.getRepository()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        repository?.let { repo ->
            repo.appList.observeForever(Observer {
                val newList: ArrayList<AppInfo> = arrayListOf()
                newList.addAll(it)
                appList = newList
            })
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn?.apply {
            val appInfo = appInList(packageName)
            if (appInfo != null) {
                with(getNotificationManager(applicationContext)) {
                    if (notification.category in categoryList && safeToPlaySound()) {
                        val uri = applicationContext.resIdToUri(getRandomAudioResource())
                        playAudioResource(uri, applicationContext)
                    }
                }
            }
        }
    }


    private fun appInList(pn: String): AppInfo? {
        appList.forEach {
            if (it.packageName == pn && it.active) return it
        }
        return null
    }

    private fun safeToPlaySound(): Boolean =
        userNotDnd() && screenIsLocked()

    private fun screenIsLocked(): Boolean = with(applicationContext) {
        val kgManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        kgManager.isKeyguardLocked
    }

    private fun userNotDnd(): Boolean =
        with(getNotificationManager(applicationContext)) {
            return when (currentInterruptionFilter) {
                NotificationManager.INTERRUPTION_FILTER_ALARMS -> false
                NotificationManager.INTERRUPTION_FILTER_PRIORITY -> false
                else -> true
            }
        }

    private fun getNotificationManager(context: Context): NotificationManager =
        context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
}