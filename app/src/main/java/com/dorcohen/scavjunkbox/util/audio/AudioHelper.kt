package com.dorcohen.scavjunkbox.util.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri

class AudioHelper(private val audioAttributes: AudioAttributes) : IAudioHelper {
    companion object {
        val notificationAtr = AudioAttributes
            .Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()

        val mediaAtr = AudioAttributes
            .Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
    }

    private val cooldown = 800
    private var lastPlayed = 0L
    private val currentTime:Long
    get() = System.currentTimeMillis()

    override fun playAudioResource(uri: Uri, context: Context) {
        try {
            with(MediaPlayer()) {
                if (currentTime - lastPlayed > cooldown) {
                    requestAudioFocus(context)
                    lastPlayed = currentTime
                    setAudioAttributes(audioAttributes)
                    setDataSource(context, uri)
                    prepare()
                    start()
                }
                setOnCompletionListener {
                    releaseAudioFocus(context)
                    release()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun requestAudioFocus(context: Context) {
        with(audioManager(context)){
            requestAudioFocus(audioFocusRequest())
        }
    }

    override fun releaseAudioFocus(context: Context) {
       with(audioManager(context)){
          abandonAudioFocusRequest(audioFocusRequest())
       }
    }

    private fun audioFocusRequest():AudioFocusRequest =
        AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
            .build()

    private fun audioManager(context: Context):AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
}