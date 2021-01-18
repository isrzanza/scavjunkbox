package com.dorcohen.scavjunkbox.util.scav

import com.dorcohen.scavjunkbox.data.model.ScavLine

interface IScavHelper {
    val scavLines:List<ScavLine>
    fun getVoiceLineResList():List<Int>
    fun getRandomAudioResource():Int
}