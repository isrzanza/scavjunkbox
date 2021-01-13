package com.dorcohen.scavjunkbox

import android.app.Application
import androidx.room.Room
import com.dorcohen.scavjunkbox.data.database.Database
import com.dorcohen.scavjunkbox.data.repository.IRepository
import com.dorcohen.scavjunkbox.data.repository.Repository

class ScavApplication : Application(), IRepoAccess {
    private lateinit var database:Database
    private lateinit var repository: IRepository

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            Database::class.java,
            "scav_database"
        ).build()

        repository = Repository(database)
    }

    override fun getRepository(): IRepository = repository
}

fun interface IRepoAccess {
    fun getRepository(): IRepository
}