package com.dorcohen.scavjunkbox.util.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.net.Uri
import android.service.notification.StatusBarNotification
import com.dorcohen.scavjunkbox.data.model.AppInfo

interface INotificationHelper {
    fun getNotificationManager(context: Context):NotificationManager
    fun createNotificationChannel(context: Context, id: String, name: String, descriptionText: String, importance: Int, sound:Uri? = null)
    fun notify(context: Context, notificationId:Int, channelId:String, title:String, body:String, action: PendingIntent? = null)
    fun rePostNotification(sbn: StatusBarNotification,appInfo:AppInfo,context: Context)
    //fun changeChannelSound(channelId:String,newSound:Uri, context: Context)
}