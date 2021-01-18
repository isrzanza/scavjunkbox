package com.dorcohen.scavjunkbox.util.notifications

import android.app.*
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.service.notification.StatusBarNotification
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.data.model.AppInfo

object NotificationHelper : INotificationHelper {

    /**
     * Notification Channel ID's
     */
    const val CHANNEL_MAIN = "scav_junkbox"
    const val CHANNEL_TEST = "scav_junkbox_test"

    override fun getNotificationManager(context: Context): NotificationManager  = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    /**
     * Notification Channels
     */
    override fun createNotificationChannel(
        context: Context,
        id: String,
        name: String,
        descriptionText: String,
        importance: Int,
        sound:Uri?
    ) {
        val channel = NotificationChannel(id, name, importance)
            .apply {
                description = descriptionText

                val attributes = AudioAttributes
                    .Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()

                setSound(sound,attributes)
            }

        //Register Channel with the system
        with(getNotificationManager(context)) {
            createNotificationChannel(channel)
        }
    }

    private fun initChannel(channelId: String, context: Context) {
        when (channelId) {
            CHANNEL_MAIN -> {
                createNotificationChannel(
                    context,
                    CHANNEL_MAIN,
                    context.getString(R.string.main_notification_channel_name),
                    context.getString(R.string.notification_channel_main_description),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            }
            CHANNEL_TEST -> {
                createNotificationChannel(
                    context,
                    CHANNEL_TEST,
                    context.getString(R.string.notification_channel_test_name),
                    context.getString(R.string.notification_channel_test_description),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            }
        }
    }

    /**
     * Create Notification
     */
    override fun notify(
        context: Context,
        notificationId:Int,
        channelId: String,
        title: String,
        body: String,
        action: PendingIntent?,
        category: String
    ) {
        //testNotification(context,0,title,body)
        initChannel(channelId, context)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(category)
        action?.apply {
            builder
                .setContentIntent(this)
                .setAutoCancel(true)
        }

        with(NotificationManagerCompat.from(context)){
            notify(notificationId,builder.build())
        }
    }


    override fun rePostNotification(sbn: StatusBarNotification,appInfo: AppInfo,context: Context) {
        sbn.notification?.let{notification ->
            with(getNotificationManager(context)){
                cancel(sbn.id) //Cancel original notification
                val newChannelId = CHANNEL_MAIN

                initChannel(newChannelId,context) //todo replace with different function
                
                val newNotification:Notification =
                    NotificationCompat.Builder(context, newChannelId)
                        .apply {
                            notification.extras.apply{
                                setContentTitle(getString(Notification.EXTRA_TITLE))
                                setContentText(getString(Notification.EXTRA_TEXT))
                            }
                            setCategory(notification.category)
                            setSmallIcon(notification.smallIcon.resId)
                            setContentIntent(notification.contentIntent)
                        }
                        .build()

                notify(sbn.id,newNotification)
            }
        }
    }

//    override fun changeChannelSound(channelId:String,newSound: Uri, context:Context) {
//        val notificationManager = NotificationManagerCompat.from(context)
//        val channel = notificationManager.getNotificationChannel(channelId)
//        channel?.apply{
//            notificationManager.deleteNotificationChannel(channelId)
//
//            val attributes = AudioAttributes
//                .Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                .build()
//
//            setSound(newSound,attributes)
//
//
//            notificationManager.createNotificationChannel(this)
//
//        }
//    }

}