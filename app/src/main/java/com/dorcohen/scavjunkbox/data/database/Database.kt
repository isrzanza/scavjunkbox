package com.dorcohen.scavjunkbox.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dorcohen.scavjunkbox.data.model.AppInfo

@Database(entities = [AppInfo::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract fun mainDao(): MainDao
}