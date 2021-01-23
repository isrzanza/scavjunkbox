package com.dorcohen.scavjunkbox.data.model

import android.content.Context
import android.net.Uri
import com.dorcohen.scavjunkbox.resIdToUri
import java.io.File
import java.io.FileOutputStream

data class ScavLine (
    val name:String = "" ,
    val resId:Int
)

fun ScavLine.getUri(context: Context): Uri = context.resIdToUri(resId)

fun List<ScavLine>.getResList():List<Int> = with(this){
    val res = arrayListOf<Int>()
    forEach { res.add(it.resId)}
    res
}

fun ScavLine.getTempFile(context:Context):File {
    val fIs = context.resources.openRawResource(resId)
    val buffer = ByteArray(fIs.available())

    with(fIs){
        read(buffer)
        close()
    }

    val fileName = "${context.resources.getResourceEntryName(resId).drop(3)}.mp3"
    val dirPath = "/${context.getExternalFilesDir(null)}/temp/"
    val filePath = "$dirPath$fileName"

    with(File(dirPath)){
        if(!exists())
            mkdirs()
    }

    val file = File(filePath)

    val fOs = FileOutputStream(filePath)

    with(fOs){
        write(buffer)
        flush()
        close()
    }

    return file
}

fun ScavLine.nameSplit() = name.split(" ")

fun ScavLine.nameNumber() = nameSplit().last()

fun ScavLine.nameNoInt() = name.dropLast(2) //might cut the last letter but it's not an issue for the use case

