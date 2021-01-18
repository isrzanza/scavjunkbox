package com.dorcohen.scavjunkbox.util.system

import androidx.lifecycle.LiveData
import com.dorcohen.scavjunkbox.data.model.SystemMessage

interface ISystemUtil {
    fun getSystemMessage(): LiveData<SystemMessage>
    fun setSystemMessage(message:SystemMessage)
    fun getString(resId:Int):String
}