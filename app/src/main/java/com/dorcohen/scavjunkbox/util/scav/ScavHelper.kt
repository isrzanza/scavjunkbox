package com.dorcohen.scavjunkbox.util.scav

import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.data.model.ScavLine
import com.dorcohen.scavjunkbox.data.model.getResList

object ScavHelper : IScavHelper {
    private var lastIndex = -1

    override val scavLines: List<ScavLine> = listOf(
    ScavLine(name = "Cheeki breeki",resId =  R.raw.cheeki_breeki),
    ScavLine(name = "Cyka", resId = R.raw.cyka),
    ScavLine(name = "Oppatski 1",R.raw.opatski),
    ScavLine(name = "Oppatski 2",R.raw.oppacski),
    ScavLine(name = "Scav laughing 1",R.raw.scav_laughing_2),
    ScavLine(name = "Scav laughing 2",R.raw.scav_laughing_3),
    ScavLine(name = "Normalnyy",R.raw.scav_normalnyy),
    ScavLine(name = "Normalnyy",R.raw.scav_normalnyy_2),
    ScavLine(name = "Vizhu pidorasa",R.raw.vizhu_pidorasa)
)


    override fun getVoiceLineResList(): List<Int> = scavLines.getResList()

    override fun getRandomAudioResource(): Int {
        val index =  kotlin.run {
            var i:Int = randomIndex()
            while(i == lastIndex){
                i = randomIndex()
            }
            lastIndex = i
            i
        }
        return scavLines[index].resId
    }

    private fun randomIndex():Int = scavLines.indices.shuffled().first()
}