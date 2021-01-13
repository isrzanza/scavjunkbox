package com.dorcohen.scavjunkbox.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dorcohen.scavjunkbox.data.model.AppInfo

@Dao
interface MainDao {
    @Insert
    suspend fun addApp(appInfo: AppInfo)

    @Update
    suspend fun toggleApp(appInfo:AppInfo)

    @Query("SELECT * FROM app_table")
    suspend fun getAppsBlocking(): List<AppInfo>

    @Query("SELECT * FROM app_table")
    fun getApps(): LiveData<List<AppInfo>>
}