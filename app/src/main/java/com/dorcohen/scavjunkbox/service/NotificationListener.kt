package com.dorcohen.scavjunkbox.service

import android.app.NotificationManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.util.audio.AudioHelper
import com.dorcohen.scavjunkbox.util.audio.IAudioHelper
import com.dorcohen.scavjunkbox.resIdToUri
import com.dorcohen.scavjunkbox.util.notifications.INotificationHelper
import com.dorcohen.scavjunkbox.util.notifications.NotificationHelper
import com.dorcohen.scavjunkbox.util.scav.IScavHelper
import com.dorcohen.scavjunkbox.util.scav.ScavHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationListener :
    NotificationListenerService(),
    IAudioHelper by AudioHelper,
    INotificationHelper by NotificationHelper,
    IScavHelper by ScavHelper {

    companion object {
        const val TAG = "NotificationListener"
    }

    private var appList:List<AppInfo>? = null

    private val tempWhiteList = arrayOf(
        "com.dorcohen.scavjunkbox"
    )

    private val tempMyChannels = arrayListOf(NotificationHelper.CHANNEL_TEST)

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {

        }
        //createChannels()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn?.apply {
            Log.d(TAG, "package name: $packageName")
            Log.d(TAG, "notification_channel, ${this.notification.channelId}")
            if (appInList(packageName) && notification.channelId !in tempMyChannels) {
                Log.d(TAG, "called repost")
                //changeChannelSound(sbn.notification.channelId,uri,applicationContext)
                //val channelName = resources.getResourceEntryName(getRandomAudioResource())

                val appInfo = AppInfo("temp","temp")//todo replace with correct app info
                rePostNotification(this, appInfo, applicationContext)
                playAudioResource(applicationContext.resIdToUri(getRandomAudioResource()),applicationContext)
            }
        }
    }

    private fun createChannels() {
        getVoiceLineResList().forEach {
            val name = resources.getResourceEntryName(it)
            tempMyChannels.add(name)
            createNotificationChannel(
                applicationContext,
                name,
                name,
                "",
                NotificationManager.IMPORTANCE_DEFAULT,
                applicationContext.resIdToUri(it)
            )
        }
    }

    private fun appInList(pn:String):Boolean{
        appList?.apply {
            if(packageName == pn)return true
        }
        return false
    }
}