package com.dorcohen.scavjunkbox.util.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import kotlinx.coroutines.withContext

class AudioHelper : IAudioHelper {
    companion object {
        private val attributes = AudioAttributes
            .Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
    }

    private var playing = false

    override fun playAudioResource(uri: Uri, context: Context) {
        try {
            with(MediaPlayer()) {
                if (!playing) {
                    requestAudioFocus(context)
                    playing = true
                    setAudioAttributes(attributes)
                    setDataSource(context, uri)
                    prepare()
                    start()
                }
                setOnCompletionListener {
                    playing = false
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