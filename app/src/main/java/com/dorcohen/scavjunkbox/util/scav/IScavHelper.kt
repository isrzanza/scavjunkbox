package com.dorcohen.scavjunkbox.util.scav

import android.content.Context
import com.dorcohen.scavjunkbox.data.model.ScavLine

interface IScavHelper {
    val scavLines:List<ScavLine>
    fun getVoiceLineResList():List<Int>
    fun getRandomAudioResource():Int
    fun generateScavLineList(context: Context)
}