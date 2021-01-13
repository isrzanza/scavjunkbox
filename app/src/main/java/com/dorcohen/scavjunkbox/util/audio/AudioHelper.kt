package com.dorcohen.scavjunkbox.util.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri

object AudioHelper : IAudioHelper {
    private val attributes = AudioAttributes
        .Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
        .build()

    override fun playAudioResource(uri: Uri, context: Context){
        try {
            with(MediaPlayer()){
                setAudioAttributes(attributes)
                setDataSource(context,uri)
                prepare()
                start()
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
}