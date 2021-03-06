package com.dorcohen.scavjunkbox.util.audio

import android.content.Context
import android.net.Uri

interface IAudioHelper {
    fun playAudioResource(uri: Uri, context: Context)
    fun requestAudioFocus(context: Context)
    fun releaseAudioFocus(context: Context)
}