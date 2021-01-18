package com.dorcohen.scavjunkbox.data.model

import android.content.Context

data class SystemMessage (val message:String,private var sent:Boolean = false){
    fun Toast(context:Context){
        if(!sent)
            android.widget.Toast.makeText(context,this.message, android.widget.Toast.LENGTH_SHORT).show()

        sent = true
    }
}