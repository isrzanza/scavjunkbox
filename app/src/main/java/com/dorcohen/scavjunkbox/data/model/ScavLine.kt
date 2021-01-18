package com.dorcohen.scavjunkbox.data.model

import android.content.Context
import android.net.Uri
import com.dorcohen.scavjunkbox.resIdToUri

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

