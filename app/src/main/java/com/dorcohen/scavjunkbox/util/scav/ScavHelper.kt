package com.dorcohen.scavjunkbox.util.scav

import com.dorcohen.scavjunkbox.R

object ScavHelper : IScavHelper {
    private var lastIndex = -1
    private val audioList = listOf(
        R.raw.cheeki_breeki,
        R.raw.cyka,
        R.raw.opatski,
        R.raw.oppacski,
        R.raw.scav_laughing_2,
        R.raw.scav_laughing_3,
        R.raw.scav_normalnyy,
        R.raw.scav_normalnyy_2,
        R.raw.vizhu_pidorasa
    )

    override fun getVoiceLineResList(): List<Int> = audioList

    override fun getRandomAudioResource(): Int {
        val index =  kotlin.run {
            var i = rand()
            while(i == lastIndex){
                i = rand()
            }
            lastIndex = i
            i
        }
        return audioList[index]
    }

    private fun rand():Int = audioList.indices.shuffled().first()
}