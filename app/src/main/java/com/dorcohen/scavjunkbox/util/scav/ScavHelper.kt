package com.dorcohen.scavjunkbox.util.scav

import android.content.Context
import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.data.model.ScavLine
import com.dorcohen.scavjunkbox.data.model.getResList
import kotlin.collections.ArrayList

object ScavHelper : IScavHelper {
    private var lastIndex = -1

    override val scavLines =  arrayListOf<ScavLine>()

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

    override fun generateScavLineList(context:Context){
        val rawResources = R.raw::class.java.fields
        rawResources.forEach {
            val resId = it.getInt(it)
            val name = generateNameFromResName(context.resources.getResourceEntryName(resId))
           scavLines.add(ScavLine(name = name,resId = resId))
        }

    }

    private fun generateNameFromResName(resName:String):String{
        return resName
            .drop(2)
            .replace("_", " ")
    }
}